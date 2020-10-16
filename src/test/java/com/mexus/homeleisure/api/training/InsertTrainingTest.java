package com.mexus.homeleisure.api.training;

import com.mexus.homeleisure.api.common.BaseControllerTest;
import com.mexus.homeleisure.api.training.request.ModifyTrainingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("트레이닝 삽입 테스트")
class InsertTrainingTest extends BaseControllerTest {

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("트레이닝 저장(성공)")
    void insertPostSuccess() throws Exception {
        String accessToken = accountFactory.generateUser(1).getAccessToken();
        ModifyTrainingRequest modifyTrainingRequest = ModifyTrainingRequest.builder()
                .title("트레이닝 제목")
                .body("트레이닝 입력 테스트입니다.")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .content(this.objectMapper.writeValueAsString(modifyTrainingRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("sendTraining",
                        requestFields(
                                fieldWithPath("title").description("트레이닝 제목"),
                                fieldWithPath("body").description("트레이닝 내용")
                        )));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/trainings/{trainingId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("트레이닝 제목"))
                .andExpect(jsonPath("body").value("트레이닝 입력 테스트입니다."));
    }
}

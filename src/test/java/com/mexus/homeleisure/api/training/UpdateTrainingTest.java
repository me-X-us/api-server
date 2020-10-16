package com.mexus.homeleisure.api.training;

import com.mexus.homeleisure.api.common.BaseControllerTest;
import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.api.training.request.ModifyTrainingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("트레이닝 수정 테스트")
class UpdateTrainingTest extends BaseControllerTest {

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("트레이닝 수정(성공)")
    void updateTrainingSuccess() throws Exception {
        Training training = this.trainingFactory.generateTraining(1);
        ModifyTrainingRequest modifyTrainingRequest = ModifyTrainingRequest.builder()
                .title("수정된 트레이닝")
                .body("트레이닝 수정 테스트입니다.")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/trainings/{trainingId}", training.getTrainingId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(modifyTrainingRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("updateTraining",
                        pathParameters(
                                parameterWithName("trainingId").description("트레이닝 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").description("트레이닝 제목"),
                                fieldWithPath("body").description("트레이닝 내용")
                        )
                ));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/trainings/{trainingId}", training.getTrainingId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("수정된 트레이닝"))
                .andExpect(jsonPath("body").value("트레이닝 수정 테스트입니다."));
    }

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("트레이닝 수정(트레이닝가 없을때)")
    void updateTrainingFailBecauseTrainingNotExist() throws Exception {
        ModifyTrainingRequest modifyTrainingRequest = ModifyTrainingRequest.builder()
                .title("수정된 트레이닝")
                .body("트레이닝 수정 테스트입니다.")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/trainings/{trainingId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(modifyTrainingRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error").value("1001"))
                .andDo(print());
    }

    @Test
    @WithMockUser("TestUser2")
    @DisplayName("트레이닝 수정(내 트레이닝가 아닐때)")
    void updateTrainingFailBecauseTrainingIsNotMine() throws Exception {
        Training training = this.trainingFactory.generateTraining(1);
        ModifyTrainingRequest modifyTrainingRequest = ModifyTrainingRequest.builder()
                .title("수정된 트레이닝")
                .body("트레이닝 수정 테스트입니다.")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/trainings/{trainingId}", training.getTrainingId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(modifyTrainingRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error").value("1001"))
                .andDo(print());
    }
}

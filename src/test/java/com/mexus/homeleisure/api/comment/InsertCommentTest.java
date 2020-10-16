package com.mexus.homeleisure.api.comment;

import com.mexus.homeleisure.api.common.BaseControllerTest;
import com.mexus.homeleisure.api.comment.request.AddCommentRequest;
import com.mexus.homeleisure.api.training.data.Training;
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

@DisplayName("댓글 삽입 테스트")
class InsertCommentTest extends BaseControllerTest {

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("댓글 쓰기(성공)")
    void saveCommentSuccess() throws Exception {
        Training training = this.trainingFactory.generateTraining(1);
        AddCommentRequest addCommentRequest = AddCommentRequest.builder()
                .message("댓글 테스트")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/trainings/{trainingId}", training
            .getTrainingId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(addCommentRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("sendComment",
                        pathParameters(
                                parameterWithName("trainingId").description("트레이닝 번호")
                        ),
                        requestFields(
                                fieldWithPath("message").description("댓글")
                        )));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/trainings/{trainingId}", training.getTrainingId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comments[0].commenterId").value("TestUser1"))
                .andExpect(jsonPath("comments[0].message").value("댓글 테스트"));
    }
}
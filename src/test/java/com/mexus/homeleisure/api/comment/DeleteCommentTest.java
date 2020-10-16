package com.mexus.homeleisure.api.comment;

import com.mexus.homeleisure.api.common.BaseControllerTest;
import com.mexus.homeleisure.api.training.data.Training;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("댓글 삭제 테스트")
class DeleteCommentTest extends BaseControllerTest {

    @Test
    @WithMockUser("TestUser2")
    @DisplayName("댓글 지우기(성공)")
    void deleteCommentSuccess() throws Exception {
        Training training = this.trainingFactory.generateTraining(1);
        long commentId = this.commentFactory.addComment(training, 2);
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/trainings/{trainingId}/{commentId}", training
            .getTrainingId(), commentId))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("deleteComment",
                        pathParameters(
                                parameterWithName("trainingId").description("트레이닝 번호"),
                                parameterWithName("commentId").description("댓글 번호")
                        )));
    }

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("댓글 지우기(트레이닝가 없을때)")
    void deleteCommentFailBecauseTrainingNotExist() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/trainings/{trainingId}/{commentId}", 1, 1))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("error").value("1101"))
                .andDo(document("1101"))
        ;
    }

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("댓글 지우기(댓글이 없을때)")
    void deleteCommentFailBecauseCommentNotExist() throws Exception {
        Training training = this.trainingFactory.generateTraining(1);
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/trainings/{trainingId}/{commentId}", training
            .getTrainingId(), 1))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("error").value("1001"))
                .andDo(document("1001"))
        ;
    }

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("댓글 지우기(내 댓글이 아닐때)")
    void deleteCommentFailBecauseCommentIsNotMine() throws Exception {
        Training training = this.trainingFactory.generateTraining(1);
        long commentId = this.commentFactory.addComment(training, 2);
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/trainings/{trainingId}/{commentId}", training
            .getTrainingId(), commentId))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("error").value("1001"))
        ;
    }
}
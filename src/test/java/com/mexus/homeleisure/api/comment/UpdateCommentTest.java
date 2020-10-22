package com.mexus.homeleisure.api.comment;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.api.comment.request.UpdateCommentRequest;
import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("댓글 수정 테스트")
class UpdateCommentTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser2")
  @DisplayName("댓글 수정하기(성공)")
  void updateCommentSuccess() throws Exception {
    Training training = this.trainingFactory.generateTraining(1);
    long commentId = this.commentFactory.addComment(training, 2);
    UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
        .message("댓글 수정 테스트")
        .build();
    this.mockMvc.perform(RestDocumentationRequestBuilders
        .put("/comments/{commentId}", commentId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(updateCommentRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("updateComment",
            pathParameters(
                parameterWithName("commentId").description("댓글 번호")
            ),
            requestFields(
                fieldWithPath("message").description("댓글")
            )));
    this.mockMvc.perform(
        RestDocumentationRequestBuilders.get("/comments/{trainingId}", training.getTrainingId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.commentList[0].message").value("댓글 수정 테스트"));
  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("댓글 수정하기(댓글이 없을때)")
  void updateCommentFailBecauseCommentNotExist() throws Exception {
    Training training = this.trainingFactory.generateTraining(1);
    UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
        .message("댓글 수정 테스트")
        .build();
    this.mockMvc.perform(RestDocumentationRequestBuilders.put("/comments/{commentId}", training
        .getTrainingId(), 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(updateCommentRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error").value("1001"))
        .andDo(print());
  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("댓글 수정하기(내 댓글이 아닐때)")
  void updateCommentFailBecauseCommentIsNotMine() throws Exception {
    Training training = this.trainingFactory.generateTraining(1);
    long commentId = this.commentFactory.addComment(training, 2);
    UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
        .message("댓글 수정 테스트")
        .build();
    this.mockMvc.perform(RestDocumentationRequestBuilders.put("/comments/{commentId}", training
        .getTrainingId(), commentId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(updateCommentRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("error").value("1001"))
        .andDo(print());
  }
}

package com.mexus.homeleisure.api.training;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.api.training.data.LikesRepository;
import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.api.training.request.ModifyTrainingRequest;
import com.mexus.homeleisure.api.training.service.TrainingService;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("트레이닝 좋아요 취소 테스트")
class UnLikeTrainingTest extends BaseControllerTest {

  @Autowired
  TrainingService trainingService;
  @Test
  @WithMockUser("TestUser1")
  @DisplayName("트레이닝 좋아요 취소 (성공)")
  void unlikeSuccess() throws Exception {
    Training training = trainingFactory.generateTraining(1);
    trainingService.likeTraining("TestUser1",training.getTrainingId());
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.delete("/trainings/{trainingId}/like", training.getTrainingId()))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("unLike"));
    this.mockMvc.perform(
        RestDocumentationRequestBuilders.get("/trainings/{trainingId}", training.getTrainingId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("likes").value(0))
        .andExpect(jsonPath("like").value(false))
        .andDo(print());
  }
}

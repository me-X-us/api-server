package com.mexus.homeleisure.api.training;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("트레이닝 좋아요 테스트")
class LikeTrainingTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("트레이닝 좋아요(성공)")
  void likeSuccess() throws Exception {
    Long trainingId = trainingFactory.generateTraining(1).getTrainingId();
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.post("/trainings/{trainingId}/like", trainingId))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("like"));
    this.mockMvc.perform(
        RestDocumentationRequestBuilders.get("/trainings/{trainingId}", trainingId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("likes").value(1))
        .andExpect(jsonPath("like").value(true))
        .andDo(print());
  }
}

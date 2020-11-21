package com.mexus.homeleisure.api.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.api.training.service.TrainingService;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("좋아요 한 트레이닝 조회")
class getLikesTest extends BaseControllerTest {

  @Autowired
  TrainingService trainingService;

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("좋아요 한 트레이닝 조회(성공)")
  void getLikesSuccess() throws Exception {
    Long trainingId1 = trainingFactory.generateTraining(1).getTrainingId();
    Long trainingId2 = trainingFactory.generateTraining(2).getTrainingId();
    trainingService.likeTraining("TestUser1",trainingId1);
    trainingService.likeTraining("TestUser1",trainingId2);

    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/training/likes"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("getLikes"))
    ;
  }
}

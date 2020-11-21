package com.mexus.homeleisure.api.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.api.training.service.TrainingService;
import com.mexus.homeleisure.api.user.service.UserService;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("트레이너 구독취소 테스트")
class UnSubscribeTest extends BaseControllerTest {

  @Autowired
  UserService userService;

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("트레이너 구독취소(성공)")
  void unSubscribeSuccess() throws Exception {
    Long trainingId = trainingFactory.generateTraining(1).getTrainingId();
    userService.subscribeTrainer("TestUser1","TestUser1");
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.delete("/trainer/{trainerId}", "TestUser1"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("unSubscribe"))
    ;

    this.mockMvc.perform(
        RestDocumentationRequestBuilders.get("/trainings/{trainingId}", trainingId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("subscribe").value(false))
        .andDo(print());
  }
}

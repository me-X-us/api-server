package com.mexus.homeleisure.api.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.api.user.service.UserService;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("구독리스트 테스트")
class GetSubscribesTest extends BaseControllerTest {

  @Autowired
  UserService userService;

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("구독 리스트 조회(성공)")
  void unSubscribeSuccess() throws Exception {
    Long trainingId = trainingFactory.generateTraining(1).getTrainingId();
    userService.subscribeTrainer("TestUser1", "TestUser1");

    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/subscribes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0].trainerId").value("TestUser1"))
        .andDo(print())
        .andDo(document("getSubscribes"))
    ;
  }
}

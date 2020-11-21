package com.mexus.homeleisure.api.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("트레이너 구독 테스트")
class SubscribeTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("트레이너 구독(성공)")
  void subscribeSuccess() throws Exception {
    Long trainingId = trainingFactory.generateTraining(1).getTrainingId();
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.post("/trainer/{trainerId}", "TestUser1"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("subscribe"))
    ;

    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/subscribes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("[0].trainerId").value("TestUser1"))
        .andDo(print())
    ;
    this.mockMvc.perform(
        RestDocumentationRequestBuilders.get("/trainings/{trainingId}", trainingId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("subscribe").value(true))
        .andDo(print());
  }
}

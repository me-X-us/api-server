package com.mexus.homeleisure.api.training;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("트레이닝 삭제 테스트")
class DeleteTrainingTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("트레이닝 삭제(성공)")
  void deleteTrainingSuccess() throws Exception {
    Training training = this.trainingFactory.generateTraining(1);
    this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/trainings/{trainingId}", training
        .getTrainingId()))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("deleteTraining",
            pathParameters(
                parameterWithName("trainingId").description("트레이닝 아이디")
            )));
  }
}

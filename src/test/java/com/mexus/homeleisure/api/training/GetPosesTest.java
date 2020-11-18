package com.mexus.homeleisure.api.training;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mexus.homeleisure.api.training.data.KeyPoints;
import com.mexus.homeleisure.api.training.data.KeyPointsRepository;
import com.mexus.homeleisure.common.BaseControllerTest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("트레이닝 포즈 조회 테스트")
class GetPosesTest extends BaseControllerTest {

  @Autowired
  private KeyPointsRepository keyPointsRepository;

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("트레이닝 포즈 조회(성공)")
  void getTrainingPosesSuccess() throws Exception {
    Path path = Paths.get(new ClassPathResource("testData.json").getURI());
    List<String> list = Files.readAllLines(path);

   List<KeyPoints> keyPointsList = new ObjectMapper().readValue(list.get(0), new TypeReference<List<KeyPoints>>(){});
   keyPointsRepository.saveAll(keyPointsList);
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/trainings/{trainingId}/poses", 1))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("getPoses"))
    ;
  }
}

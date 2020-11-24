package com.mexus.homeleisure.api.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.api.user.request.UpdateProfileRequest;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("프로필 조회 테스트")
class getProfileTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("프로필 조회(성공)")
  void updateProfileSuccess() throws Exception {
    accountFactory.generateUser(1);

    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/profile"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("getProfile"))
    ;
  }
}

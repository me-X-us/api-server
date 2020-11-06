package com.mexus.homeleisure.api.image;

import com.mexus.homeleisure.api.image.data.ProfileImage;
import com.mexus.homeleisure.api.image.data.ProfileImageRepository;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.io.File;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("프로필 이미지 테스트")
class GetProfileImageTest extends BaseControllerTest {
    @Autowired
    ProfileImageRepository profileImageRepository;

    @Test
    @DisplayName("프로필 이미지 받기(성공)")
    void getProfileImageSuccess() throws Exception {
        accountFactory.generateUser(1);
        File targetFile = new File("./files/profileImg/test.jpg");

        ProfileImage profileImage = new ProfileImage("TestUser1", targetFile.getPath());
        profileImageRepository.save(profileImage);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/profile/{userId}/image", "TestUser1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getProfileImage",
                        pathParameters(
                                parameterWithName("userId").description("user id")
                        )
                ));
    }

    @Test
    @DisplayName("프로필 이미지 받기(이미지가 없을 때)")
    void getProfileImageFailBecauseNotFound() throws Exception {

        this.mockMvc.perform(get("/profile/{userId}/image", "TestUser1"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(document("3002"));
    }
}
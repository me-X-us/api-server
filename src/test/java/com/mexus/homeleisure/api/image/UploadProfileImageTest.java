package com.mexus.homeleisure.api.image;

import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("프로필 이미지 업로드 테스트")
class UploadProfileImageTest extends BaseControllerTest {

    @Test
    @DisplayName("프로필 이미지 업로드")
    void uploadProfileImageSuccess() throws Exception {
        String accessToken = accountFactory.generateUser(1).getAccessToken();
        File targetFile = new File("./files/profileImg/test.jpg");
        MockMultipartFile image = new MockMultipartFile(
                "image", targetFile.getName(), "image/jpeg", new FileInputStream(targetFile));

        this.mockMvc.perform(RestDocumentationRequestBuilders.fileUpload("/profile/{userId}/image", "TestUser1").file(image)
                .accept(MediaTypes.HAL_JSON)
                .header("Authorization", "Bearer " + accessToken)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("uploadProfileImage",
                        pathParameters(
                                parameterWithName("userId").description("user id")
                        )
                ))
        ;
        this.mockMvc.perform(get("/profile/{userId}/image", "TestUser1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("프로필 이미지 업로드")
    void uploadProfileImageFailBecauseBadFileName() throws Exception {
        String accessToken = accountFactory.generateUser(1).getAccessToken();
        File targetFile = new File("./files/test..jpg");
        MockMultipartFile image = new MockMultipartFile(
            "image", targetFile.getName(), "image/jpeg", new FileInputStream(targetFile));

        this.mockMvc.perform(RestDocumentationRequestBuilders.fileUpload("/profile/{userId}/image", "TestUser1").file(image)
            .accept(MediaTypes.HAL_JSON)
            .header("Authorization", "Bearer " + accessToken)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andDo(print())
            .andDo(document("3001"));
    }
}
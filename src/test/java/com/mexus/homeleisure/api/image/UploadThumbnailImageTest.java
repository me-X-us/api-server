package com.mexus.homeleisure.api.image;

import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("썸네일 이미지 업로드 테스트")
class UploadThumbnailImageTest extends BaseControllerTest {

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("썸네일 이미지 업로드(성공)")
    void uploadThumbnailImageSuccess() throws Exception {
        Training training = this.trainingFactory.generateTraining(1);

        File targetFile = new File("./files/thumbnailImg/test.jpg");
        MockMultipartFile image = new MockMultipartFile(
                "image", targetFile.getName(), "image/jpeg", new FileInputStream(targetFile));

        this.mockMvc.perform(RestDocumentationRequestBuilders.fileUpload("/thumbnail/{trainingId}", 1).file(image)
                .accept(MediaTypes.HAL_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andDo(document("uploadThumbnailImage",
                        pathParameters(
                                parameterWithName("trainingId").description("user id")
                        )
                ))
        ;
        this.mockMvc.perform(get("/thumbnail/{trainingId}", 1))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
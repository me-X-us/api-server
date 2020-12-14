package com.mexus.homeleisure.api.image;

import com.mexus.homeleisure.api.image.data.ThumbnailImage;
import com.mexus.homeleisure.api.image.data.ThumbnailImageRepository;
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

@DisplayName("썸네일 테스트")
class GetThumbnailImageTest extends BaseControllerTest {
    @Autowired
    ThumbnailImageRepository thumbnailImageRepository;

    @Test
    @DisplayName("썸네일 이미지 받기(성공)")
    void getThumbnailImageSuccess() throws Exception {
        accountFactory.generateUser(1);
        File targetFile = new File("./files/thumbnailImg/test.jpg");

        ThumbnailImage thumbnailImage = new ThumbnailImage("1", targetFile.getPath());
        thumbnailImageRepository.save(thumbnailImage);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/thumbnail/{trainingId}", 1))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getThumbnailImage",
                        pathParameters(
                                parameterWithName("trainingId").description("트레이닝 번호")
                        )
                ));
    }

    @Test
    @DisplayName("썸네일 이미지 받기(이미지가 없을 때)")
    void getThumbnailImageFailBecauseNotFound() throws Exception {

        this.mockMvc.perform(get("/thumbnail/{trainingId}", 1))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
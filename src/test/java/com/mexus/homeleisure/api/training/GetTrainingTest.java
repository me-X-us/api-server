package com.mexus.homeleisure.api.training;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("트레이닝 조회 테스트")
class GetTrainingTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("트레이닝 목록 조회(성공)")
  void getTrainingsSuccess() throws Exception {
    this.trainingFactory.generateTraining(1);
    this.trainingFactory.generateTraining(2);
    this.trainingFactory.generateTraining(3);
    this.trainingFactory.generateTraining(4);
    this.trainingFactory.generateTraining(5);
    this.trainingFactory.generateTraining(6);
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/trainings")
        .param("page", "1")
        .param("size", "2")
        .param("sort", "title,DESC"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("getTrainings",
            responseFields(
                fieldWithPath("_embedded.trainingList[].trainingId").description("트레이닝 아이디"),
                fieldWithPath("_embedded.trainingList[].title").description("트레이닝 제목"),
                fieldWithPath("_embedded.trainingList[].trainer").description("트레이너"),
                fieldWithPath("_embedded.trainingList[].views").description("조회수"),
                fieldWithPath("_embedded.trainingList[].modifiedDate").description("수정일"),
                fieldWithPath("_embedded.trainingList[]._links.self.href")
                    .description("트레이닝 데이터 링크"),
                fieldWithPath("_links.self.href").description("Self 링크"),
                fieldWithPath("_links.profile.href").description("해당 링크의 Api Docs"),
                fieldWithPath("_links.first.href").description("첫번째 목록"),
                fieldWithPath("_links.prev.href").description("이전 목록"),
                fieldWithPath("_links.next.href").description("다음 목록"),
                fieldWithPath("_links.last.href").description("마지막 목록"),
                fieldWithPath("page.size").description("한 페이지에 조회되는 트레이닝수"),
                fieldWithPath("page.totalElements").description("총 트레이닝수"),
                fieldWithPath("page.totalPages").description("총 페이지"),
                fieldWithPath("page.number").description("현재 페이지")
            )
        ));
  }

  @Test
  @DisplayName("트레이닝 조회(성공)")
  void getTrainingSuccess() throws Exception {
    Training training = this.trainingFactory.generateTraining(1);
    this.commentFactory.addComment(training, 2);
    this.commentFactory.addComment(training, 3);
    this.mockMvc.perform(
        RestDocumentationRequestBuilders.get("/trainings/{trainingId}", training.getTrainingId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("title").value("트레이닝1"))
        .andExpect(jsonPath("trainerId").value("TestUser1"))
        .andExpect(jsonPath("body").value("트레이닝 본문입니다."))
        .andDo(print())
        .andDo(document("getTraining",
            pathParameters(
                parameterWithName("trainingId").description("트레이닝 아이디")
            ),
            responseFields(
                fieldWithPath("title").description("트레이닝 제목"),
                fieldWithPath("trainer").description("트레이너"),
                fieldWithPath("trainerId").description("트레이너 아이디"),
                fieldWithPath("body").description("트레이닝 내용"),
                fieldWithPath("likes").description("좋아요수"),
                fieldWithPath("views").description("조회수"),
                fieldWithPath("subscribe").description("구독 여부"),
                fieldWithPath("like").description("좋아요 했는지 여부"),
                fieldWithPath("keep").description("담았는지 여부"),
                fieldWithPath("createdDate").description("작성일"),
                fieldWithPath("modifiedDate").description("수정일"),
                fieldWithPath("_links.self.href").description("Self 링크"),
                fieldWithPath("_links.sendComment.href").description("트레이닝에 댓글달기"),
                fieldWithPath("_links.profile.href").description("해당 링크의 Api Docs")
            ),
            links(
                linkWithRel("self").description("Self 링크"),
                linkWithRel("sendComment").description("트레이닝에 댓글달기"),
                linkWithRel("profile").description("해당 링크의 Api Docs")
            )
        ));

  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("나의 트레이닝 조회(성공)")
  void getMyTrainingSuccess() throws Exception {
    Training training = this.trainingFactory.generateTraining(1);
    this.commentFactory.addComment(training, 2);
    this.commentFactory.addComment(training, 3);
    this.mockMvc.perform(
        RestDocumentationRequestBuilders.get("/trainings/{trainingId}", training.getTrainingId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("title").value("트레이닝1"))
        .andExpect(jsonPath("trainerId").value("TestUser1"))
        .andExpect(jsonPath("body").value("트레이닝 본문입니다."))
        .andDo(print())
        .andDo(document("getMyTraining",
            pathParameters(
                parameterWithName("trainingId").description("트레이닝 아이디")
            ),
            responseFields(
                fieldWithPath("title").description("트레이닝 제목"),
                fieldWithPath("trainerId").description("트레이너 아이디"),
                fieldWithPath("trainer").description("트레이너"),
                fieldWithPath("body").description("트레이닝 내용"),
                fieldWithPath("likes").description("좋아요수"),
                fieldWithPath("views").description("조회수"),
                fieldWithPath("subscribe").description("구독 여부"),
                fieldWithPath("like").description("좋아요 했는지 여부"),
                fieldWithPath("keep").description("담았는지 여부"),
                fieldWithPath("createdDate").description("작성일"),
                fieldWithPath("modifiedDate").description("수정일"),
                fieldWithPath("_links.self.href").description("Self 링크"),
                fieldWithPath("_links.updateTraining.href").description("트레이닝 수정하기"),
                fieldWithPath("_links.deleteTraining.href").description("트레이닝 삭제하기"),
                fieldWithPath("_links.sendComment.href").description("트레이닝에 댓글달기"),
                fieldWithPath("_links.profile.href").description("해당 링크의 Api Docs")
            ),
            links(
                linkWithRel("self").description("Self 링크"),
                linkWithRel("updateTraining").description("트레이닝 수정하기"),
                linkWithRel("deleteTraining").description("트레이닝 삭제하기"),
                linkWithRel("sendComment").description("트레이닝에 댓글달기"),
                linkWithRel("profile").description("해당 링크의 Api Docs")
            )
        ));

  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("트레이닝 조회(트레이닝이 없을때)")
  void getTrainingNoTrainingFailBecauseNotFound() throws Exception {
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/trainings/{trainingId}", 1))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("error").value("1101"))
        .andDo(print())
        .andDo(document("1101"));
  }
}

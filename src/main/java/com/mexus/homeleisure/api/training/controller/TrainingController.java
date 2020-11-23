package com.mexus.homeleisure.api.training.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.mexus.homeleisure.api.training.dto.TrainingDetailDto;
import com.mexus.homeleisure.api.training.dto.TrainingsDto;
import com.mexus.homeleisure.api.training.request.ModifyTrainingRequest;
import com.mexus.homeleisure.api.training.response.PosesResponse;
import com.mexus.homeleisure.api.training.response.TrainingResponse;
import com.mexus.homeleisure.api.training.response.TrainingsResponse;
import com.mexus.homeleisure.api.training.service.TrainingService;
import com.mexus.homeleisure.common.DocsController;
import com.mexus.homeleisure.common.response.LinksResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 트레이닝 컨트롤러
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/trainings", produces = MediaTypes.HAL_JSON_VALUE)
public class TrainingController {

  private final TrainingService trainingService;

  /**
   * 모든 트레이닝 조회(Paged)
   * body랑 comments가 조회 안되게 수정필요
   *
   * @param pageable  페이지 정보
   * @param assembler 어셈블러
   * @return 페이징 처리된 트레이닝
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public PagedModel<TrainingsResponse> getTrainings(
      @RequestParam(required = false) String search,
      Pageable pageable,
      PagedResourcesAssembler<TrainingsDto> assembler
  ) {
    Page<TrainingsDto> trainings;
    if(search!=null)
        trainings = this.trainingService.searchTrainings(search, pageable);
    else
        trainings = this.trainingService.getTrainings(pageable);
    PagedModel<TrainingsResponse> trainingsResponses =
        assembler.toModel(trainings, trainingsDto -> new TrainingsResponse(trainingsDto));
    trainingsResponses.add(linkTo(DocsController.class).slash("#getTrainings").withRel("profile"));

    return trainingsResponses;
  }

  /**
   * 트레이닝 작성
   *
   * @param modifyTrainingRequest 트레이닝 정보
   * @param response              헤더 설정을 위한 response 객체
   * @return self 링크, API Docs 링크
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LinksResponse saveTraining(
      @RequestBody ModifyTrainingRequest modifyTrainingRequest,
      HttpServletResponse response

  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    Long trainingId = this.trainingService.saveTraining(requestUserId, modifyTrainingRequest);

    response.setHeader("Location",
        linkTo(TrainingController.class).slash(trainingId).toUri().toString());
    return new LinksResponse(
        linkTo(TrainingController.class).slash(trainingId).withSelfRel(),
        linkTo(DocsController.class).slash("#sendTraining").withRel("profile")
    );
  }

  /**
   * 트레이닝 조회
   *
   * @param trainingId 트레이닝 Id
   * @return 트레이닝
   */
  @GetMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public TrainingResponse getTraining(
      @PathVariable Long trainingId
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    TrainingDetailDto training = this.trainingService.getTraining(requestUserId, trainingId);
    TrainingResponse trainingResponse = new TrainingResponse(training, trainingId);
    if (SecurityContextHolder.getContext().getAuthentication().getName()
        .equals(training.getTrainerId())) {
      trainingResponse
          .add(linkTo(TrainingController.class).slash(trainingId).withRel("updateTraining"));
      trainingResponse
          .add(linkTo(TrainingController.class).slash(trainingId).withRel("deleteTraining"));
    }
    return trainingResponse;
  }

  @GetMapping("/{trainingId}/poses")
  @ResponseStatus(HttpStatus.OK)
  public PosesResponse getPoses(
      @PathVariable Long trainingId
  ) {
    return new PosesResponse(this.trainingService.getPoses(trainingId));
  }


  /**
   * 트레이닝 수정
   *
   * @param trainingId            트레이닝 Id
   * @param modifyTrainingRequest 트레이닝 정보
   * @return self 링크, API Docs 링크
   */
  @PutMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public LinksResponse updateTraining(
      @PathVariable Long trainingId,
      @RequestBody ModifyTrainingRequest modifyTrainingRequest
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.trainingService.updateTraining(trainingId, requestUserId, modifyTrainingRequest);

    return new LinksResponse(
        linkTo(TrainingController.class).slash(trainingId).withSelfRel(),
        linkTo(DocsController.class).slash("#updateTraining").withRel("profile")
    );
  }

  /**
   * 트레이닝 삭제
   *
   * @param trainingId 트레이닝 ID
   * @return self 링크, API Docs 링크
   */
  @DeleteMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public LinksResponse deleteTraining(
      @PathVariable Long trainingId
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.trainingService.deleteTraining(trainingId, requestUserId);

    return new LinksResponse(
        linkTo(TrainingController.class).slash(trainingId).withSelfRel(),
        linkTo(DocsController.class).slash("#deleteTraining").withRel("profile")
    );
  }


  /**
   * 트레이닝 좋아요
   */
  @PostMapping("/{trainingId}/like")
  @ResponseStatus(HttpStatus.OK)
  public void likeTraining(
      @PathVariable Long trainingId
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.trainingService.likeTraining(requestUserId, trainingId);
  }

  /**
   * 트레이닝 좋아요 취소
   */
  @DeleteMapping("/{trainingId}/like")
  @ResponseStatus(HttpStatus.OK)
  public void unLikeTraining(
      @PathVariable Long trainingId
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.trainingService.unLikeTraining(requestUserId, trainingId);
  }
}

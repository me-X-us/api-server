package com.mexus.homeleisure.api.training.service;

import com.mexus.homeleisure.api.training.data.KeyPoints;
import com.mexus.homeleisure.api.training.data.KeyPointsRepository;
import com.mexus.homeleisure.api.training.data.Likes;
import com.mexus.homeleisure.api.training.data.LikesRepository;
import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.api.training.data.TrainingRepository;
import com.mexus.homeleisure.api.training.dto.FrameDto;
import com.mexus.homeleisure.api.training.dto.KeyPointDto;
import com.mexus.homeleisure.api.training.dto.TrainingDetailDto;
import com.mexus.homeleisure.api.training.dto.TrainingsDto;
import com.mexus.homeleisure.api.training.exception.TrainingNotFoundException;
import com.mexus.homeleisure.api.training.request.ModifyTrainingRequest;
import com.mexus.homeleisure.api.user.data.SubscribeRepository;
import com.mexus.homeleisure.api.user.data.Users;
import com.mexus.homeleisure.api.user.data.UsersRepository;
import com.mexus.homeleisure.api.user.exception.InvalidUserException;
import com.mexus.homeleisure.common.exception.ThisIsNotYoursException;
import com.mexus.homeleisure.security.data.UserStatus;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 트레이닝 서비스
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TrainingService {

  private final TrainingRepository trainingRepository;
  private final UsersRepository usersRepository;
  private final KeyPointsRepository keyPointsRepository;
  private final LikesRepository likesRepository;
  private final SubscribeRepository subscribeRepository;

  /**
   * 모든 트레이닝 조회(Paged)
   *
   * @param pageable 페이지 정보
   * @return 페이징 처리가 된 트레이닝
   */
  @Transactional
  public Page<TrainingsDto> getTrainings(Pageable pageable) {
    return this.trainingRepository.findAllProjectedBy(pageable);
  }


  public Page<TrainingsDto> searchTrainings(String search, Pageable pageable) {
    return this.trainingRepository.findAllByTitleContaining(search, pageable);
  }

  /**
   * 트레이닝 작성
   *
   * @param modifyTrainingRequest 트레이닝 정보
   * @return 트레이닝 ID
   */
  @Transactional
  public Long saveTraining(String requestUserId, ModifyTrainingRequest modifyTrainingRequest) {
    return this.trainingRepository.save(
        new Training(
            null,
            usersRepository.findByUserIdAndState(requestUserId, UserStatus.NORMAL, Users.class)
                .orElseThrow(
                    InvalidUserException::new),
            modifyTrainingRequest.getTitle(),
            modifyTrainingRequest.getBody()
        )).getTrainingId();
  }

  /**
   * 트레이닝 조회
   *
   * @param trainingId 트레이닝 Id
   * @return 트레이닝
   * @throws TrainingNotFoundException 존재하지 않는 트레이닝입니다.
   */
  @Transactional
  public TrainingDetailDto getTraining(String requestUserId, Long trainingId) {
    Training training = this.trainingRepository.findByTrainingId(trainingId)
        .orElseThrow(TrainingNotFoundException::new);
    training.increaseViews();
    likesRepository.existsByUser_UserIdAndTraining_TrainingId(requestUserId, trainingId);

    return TrainingDetailDto.builder()
        .title(training.getTitle())
        .body(training.getBody())
        .trainerId(training.getTrainer().getUserId())
        .trainer(training.getTrainer().getName())
        .createdDate(training.getCreatedDate())
        .modifiedDate(training.getModifiedDate())
        .like(likesRepository.existsByUser_UserIdAndTraining_TrainingId(requestUserId, trainingId))
        .subscribe(subscribeRepository
            .existsByTrainer_UserIdAndUser_UserId(training.getTrainer().getUserId(), requestUserId))
        .likes(training.getLikes())
        .views(training.getViews())
        .build();
  }

  /**
   * 트레이닝 수정
   *
   * @param trainingId            트레이닝 ID
   * @param modifyTrainingRequest 트레이닝 정보
   */
  @Transactional
  public void updateTraining(Long trainingId, String requestUserId,
                             ModifyTrainingRequest modifyTrainingRequest) {
    getMyTraining(trainingId, requestUserId)
        .updateTraining(modifyTrainingRequest.getTitle(), modifyTrainingRequest.getBody());
  }

  /**
   * 트레이닝 삭제
   *
   * @param trainingId 트레이닝 ID
   */
  @Transactional
  public void deleteTraining(Long trainingId, String requestUserId) {
    this.trainingRepository.deleteById(getMyTraining(trainingId, requestUserId).getTrainingId());
  }

  /**
   * 내 트레이닝 가져오기
   *
   * @param trainingId 트레이닝 ID
   * @return 트레이닝 엔터티
   * @throws ThisIsNotYoursException 수정권한이 없습니다.
   */
  private Training getMyTraining(Long trainingId, String requestUserId) {
    return trainingRepository.findByTrainingIdAndTrainer_UserId(trainingId, requestUserId)
        .orElseThrow(ThisIsNotYoursException::new);
  }

  public List<FrameDto> getPoses(Long trainingId) {
    List<KeyPoints> keyPoints = keyPointsRepository.findAllByTrainingId(trainingId);
    int frameNo = 0;
    List<FrameDto> frames = new ArrayList<FrameDto>();
    FrameDto frame = new FrameDto(0);
    for (KeyPoints keyPoint : keyPoints) {
      if (keyPoint.getFrameNo() != frameNo) {
        frames.add(frame);
        frame = new FrameDto(++frameNo);
      }
      frame.addKeyPoint(new KeyPointDto(keyPoint));
    }
    return frames;
  }

  @Transactional
  public void likeTraining(String requestUserId, Long trainingId) {
    Training training = trainingRepository.findByTrainingId(trainingId)
        .orElseThrow(TrainingNotFoundException::new);
    training.increaseLikes();
    likesRepository.save(
        new Likes(
            usersRepository.findByUserIdAndState(requestUserId, UserStatus.NORMAL, Users.class)
                .orElseThrow(InvalidUserException::new),
            training
        ));
  }

  @Transactional
  public void unLikeTraining(String requestUserId, Long trainingId) {
    likesRepository.deleteByUser_UserIdAndTraining_TrainingId(requestUserId, trainingId);
    Training training = trainingRepository.findByTrainingId(trainingId)
        .orElseThrow(TrainingNotFoundException::new);
    training.decreaseLikes();
  }
}

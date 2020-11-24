package com.mexus.homeleisure.api.training.dto;

import java.time.LocalDateTime;

import com.mexus.homeleisure.api.training.data.Training;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

/**
 * 트레이닝 리스트 데이터 전송 객체
 * 내부에서 데이터 이동시 사용된다.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@AllArgsConstructor
@Relation(collectionRelation = "trainingList")
public class TrainingsDto {
  /**
   * 트레이닝 ID
   */
  private final Long trainingId;
  /**
   * 트레이닝 제목
   */
  private final String title;
  /**
   * 트레이닝 제목
   */
  private final String body;
  /**
   * 트레이너 이름
   */
  private final String trainer;
  /**
   * 조회수
   */
  private final Long views;
  /**
   * 수정일
   */
  private final LocalDateTime modifiedDate;

  public TrainingsDto(Training training){
    this.trainingId = training.getTrainingId();
    this.title = training.getTitle();
    this.trainer = training.getTrainer().getName();
    this.views = training.getViews();
    this.modifiedDate = training.getModifiedDate();
    this.body = training.getBody();
  }
}

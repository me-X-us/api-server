package com.mexus.homeleisure.api.training.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * 트레이닝 상세 데이터 전송 객체
 * 내부에서 데이터 이동시 사용된다.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
public class TrainingDetailDto {
  /**
   * 트레이닝 제목
   */
  private final String title;
  /**
   * 트레이너 ID
   */
  private final String trainerId;
  /**
   * 트레이너
   */
  private final String trainer;
  /**
   * 트레이닝 본문
   */
  private final String body;
  /**
   * 조회수
   */
  private final Long views;
  /**
   * 작성일
   */
  private final LocalDateTime createdDate;
  /**
   * 수정일
   */
  private final LocalDateTime modifiedDate;

  @Builder
  public TrainingDetailDto(String title, String trainerId, String trainer, String body, Long views,
                           LocalDateTime createdDate, LocalDateTime modifiedDate) {
    this.title = title;
    this.trainerId = trainerId;
    this.trainer = trainer;
    this.body = body;
    this.views = views;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }
}

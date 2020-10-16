package com.mexus.homeleisure.api.training.data;

import com.mexus.homeleisure.api.training.dto.TrainingsDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 트레이닝 레포지터리
 *
 * @author always0ne
 * @version 1.0
 */
@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
  /**
   * 트레이닝 아이디로 조회
   *
   * @param trainingId 트레이닝 아이디
   * @return 트레이닝(Optional)
   */
  Optional<Training> findByTrainingId(Long trainingId);

  /**
   * 사용자의 트레이닝인지 확인하며 트레이닝 조회
   *
   * @param trainingId 트레이닝 아이디
   * @return 트레이닝(Optional)
   */
  Optional<Training> findByTrainingIdAndAuthor_UserId(Long trainingId, String userId);

  /**
   * 모든 트레이닝 조회(Pagenation)
   *
   * @param pageable 페이지 정보
   * @return 모든 트레이닝(Page)
   */
  @Query(value = "SELECT new com.mexus.homeleisure.api.training.dto.TrainingsDto(" +
      "trainingId, title, author.userId, views, commentNum, modifiedDate)" +
      " FROM Training",
      countQuery = "SELECT count(trainingId) FROM Training")
  Page<TrainingsDto> findAllProjectedBy(Pageable pageable);
}

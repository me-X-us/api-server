package com.mexus.homeleisure.api.training.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyPointsRepository extends JpaRepository<KeyPoints,Long> {
  List<KeyPoints> findAllByTrainingId(long trainingId);
}

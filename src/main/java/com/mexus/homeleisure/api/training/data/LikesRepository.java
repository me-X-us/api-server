package com.mexus.homeleisure.api.training.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    void deleteByUser_UserIdAndTraining_TrainingId(String userId, long trainingId);

    List<Likes> findAllByUser_UserId(String userId);
}

package com.mexus.homeleisure.api.user.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    void deleteByTrainer_UserIdAndUser_UserId(String trainerId, String userId);
    boolean existsByTrainer_UserIdAndUser_UserId(String trainerId, String userId);
    List<Subscribe> findAllByUser_UserId(String userId);
}

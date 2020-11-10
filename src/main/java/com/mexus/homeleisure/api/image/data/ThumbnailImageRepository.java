package com.mexus.homeleisure.api.image.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThumbnailImageRepository extends JpaRepository<ThumbnailImage, Integer> {
    Optional<ThumbnailImage> findByTrainingId(String trainingId);
}

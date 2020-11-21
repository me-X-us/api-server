package com.mexus.homeleisure.api.image.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ThumbnailImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String trainingId;
    private String filePath;

    public ThumbnailImage(String trainingId, String filePath) {
        this.trainingId = trainingId;
        this.filePath = filePath;
    }

    public void updateThumbnailImage(String trainingId, String filePath) {
        this.trainingId = trainingId;
        this.filePath = filePath;
    }
}
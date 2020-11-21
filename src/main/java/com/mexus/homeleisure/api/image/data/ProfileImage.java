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
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String userId;
    private String filePath;

    public ProfileImage(String userId, String filePath) {
        this.userId = userId;
        this.filePath = filePath;
    }
    public void updateProfileImage(String userId, String filePath) {
        this.userId = userId;
        this.filePath = filePath;
    }
}
package com.mexus.homeleisure.api.image.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "imageId")
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String userId;
    private String filePath;

    public void updateProfileImage(String userId, String filePath) {
        this.userId = userId;
        this.filePath = filePath;
    }
}
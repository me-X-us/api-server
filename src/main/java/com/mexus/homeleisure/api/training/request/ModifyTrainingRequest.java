package com.mexus.homeleisure.api.training.request;

import lombok.*;

/**
 * 트레이닝 작성,수정 요청
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyTrainingRequest {
    /**
     * 트레이닝 제목
     */
    private String title;
    /**
     * 트레이닝 본문
     */
    private String body;
}

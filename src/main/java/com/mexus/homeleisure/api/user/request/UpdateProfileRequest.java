package com.mexus.homeleisure.api.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

  /**
   * 트레이닝 본문
   */
  private String nickName;
}

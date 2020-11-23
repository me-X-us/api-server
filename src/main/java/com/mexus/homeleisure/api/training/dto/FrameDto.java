package com.mexus.homeleisure.api.training.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FrameDto {

  private int frameNo;
  private final List<KeyPointDto> keyPoint = new ArrayList<KeyPointDto>();

  public FrameDto(int frameNo){
    this.frameNo = frameNo;
  }

  public void addKeyPoint(KeyPointDto keyPointDto){
    this.keyPoint.add(keyPointDto);
  }
}

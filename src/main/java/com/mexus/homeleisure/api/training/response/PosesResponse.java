package com.mexus.homeleisure.api.training.response;

import com.mexus.homeleisure.api.training.dto.FrameDto;
import java.util.List;
import lombok.Getter;

@Getter
public class PosesResponse {
  private final List<FrameDto> frames;

  public PosesResponse(List<FrameDto> frameDtos){
    this.frames = frameDtos;
  }
}

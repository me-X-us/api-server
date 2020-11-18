package com.mexus.homeleisure.api.training.dto;

import com.mexus.homeleisure.api.training.data.KeyPoints;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "keyPoint")
public class KeyPointDto {
  private final double y;
  private final double x;
  private final String part;
  private final double score;

  public KeyPointDto(KeyPoints keyPoints) {
    this.x = keyPoints.getX();
    this.y = keyPoints.getY();
    this.part = keyPoints.getPart();
    this.score = keyPoints.getScore();
  }
}

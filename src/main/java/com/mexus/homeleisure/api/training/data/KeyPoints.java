package com.mexus.homeleisure.api.training.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyPoints {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long KeyPointId;

  private long trainingId;
  private int frameNo;
  private double y;
  private double x;
  private String part;
  private double score;
}

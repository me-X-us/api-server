package com.mexus.homeleisure.api.training.data;

import com.mexus.homeleisure.api.comment.data.Comment;
import com.mexus.homeleisure.api.user.data.Users;
import com.mexus.homeleisure.common.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 트레이닝 엔터티
 *
 * @author always0ne
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@Getter
public class Training extends Date {
  /**
   * pk
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long trainingId;
  /**
   * 글 제목
   */
  @Column(length = 100, nullable = false)
  private String title;
  /**
   * 본문
   */
  @Column(columnDefinition = "TEXT", nullable = false)
  private String body;
  /**
   * 조회수
   */
  @Column(nullable = false)
  private Long views;
  /**
   * 댓글수
   */
  @Column(nullable = false)
  private Long commentNum;
  /**
   * 작성자
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author")
  private Users trainer;
  /**
   * 댓글들
   *
   * @see Comment
   */
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "training_id")
  private List<Comment> comments;

  public Training(Long id, Users trainer, String title, String body) {
    super();
    this.trainingId = id;
    this.trainer = trainer;
    this.title = title;
    this.body = body;
    this.views = (long) 0;
    this.commentNum = (long) 0;
  }

  /**
   * 조회수 증가
   */
  public void increaseViews() {
    this.views++;
  }

  /**
   * 트레이닝 수정
   * 데이터 변경
   *
   * @param title 글 제목
   * @param body  글 본문
   */
  public void updateTraining(String title, String body) {
    this.title = title;
    this.body = body;
    this.updateModifyDate();
  }

  /**
   * 댓글 추가
   *
   * @param comment 댓글
   */
  public void addComment(Comment comment) {
      if (this.comments == null) {
          this.comments = new ArrayList<Comment>();
      }
    this.comments.add(comment);
    this.commentNum++;
  }
}

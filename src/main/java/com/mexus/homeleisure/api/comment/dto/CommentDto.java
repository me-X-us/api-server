package com.mexus.homeleisure.api.comment.dto;

import com.mexus.homeleisure.api.comment.data.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

/**
 * 댓글 리스트 데이터 전송 객체
 * 내부에서 데이터 이동시 사용된다.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@Relation(collectionRelation = "commentList")
public class CommentDto {
  /**
   * 댓글 ID
   */
  private Long commentId;
  /**
   * 댓글 본문
   */
  private String message;
  /**
   * 작성자 ID
   */
  private String commenterId;
  /**
   * 작성일
   */
  private LocalDateTime createdDate;
  /**
   * 수정일
   */
  private LocalDateTime modifiedDate;

  public CommentDto(Comment comment) {
    this.commentId = comment.getCommentId();
    this.message = comment.getMessage();
    this.commenterId = comment.getAuthor().getUserId();
    this.createdDate = comment.getCreatedDate();
    this.modifiedDate = comment.getModifiedDate();
  }
}

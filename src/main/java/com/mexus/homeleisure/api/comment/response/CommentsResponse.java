package com.mexus.homeleisure.api.comment.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.mexus.homeleisure.api.comment.data.Comment;
import com.mexus.homeleisure.api.comment.dto.CommentDto;
import com.mexus.homeleisure.api.training.controller.TrainingController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

/**
 * 댓글 정보
 *
 * @author always0ne
 * @version 1.0
 */
public class CommentsResponse extends EntityModel<CommentDto> {

  public CommentsResponse(Comment comment, Link... links) {
    super(new CommentDto(comment), links);
    add(linkTo(TrainingController.class).slash(comment.getCommentId()).withSelfRel());
    add(linkTo(TrainingController.class).slash(comment.getCommentId()).withRel("updateComment"));
    add(linkTo(TrainingController.class).slash(comment.getCommentId()).withRel("deleteComment"));
  }
}

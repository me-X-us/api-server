package com.mexus.homeleisure.api.comment.data;

import com.mexus.homeleisure.api.comment.dto.CommentDto;
import com.mexus.homeleisure.api.training.controller.TrainingController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
/**
 * 댓글 정보
 *
 * @author always0ne
 * @version 1.0
 */
public class CommentResource extends EntityModel<CommentDto> {

    public CommentResource(Comment comment, Link... links) {
        super(new CommentDto(comment), links);
        add(linkTo(TrainingController.class).slash(comment.getCommentId()).withSelfRel());
        add(linkTo(TrainingController.class).slash(comment.getCommentId()).withRel("updateComment"));
        add(linkTo(TrainingController.class).slash(comment.getCommentId()).withRel("deleteComment"));
    }
}

package com.mexus.homeleisure.api.comment.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.mexus.homeleisure.api.comment.data.Comment;
import com.mexus.homeleisure.api.comment.dto.CommentDto;
import com.mexus.homeleisure.api.comment.request.AddCommentRequest;
import com.mexus.homeleisure.api.comment.request.UpdateCommentRequest;
import com.mexus.homeleisure.api.comment.response.CommentsResponse;
import com.mexus.homeleisure.api.comment.service.CommentService;
import com.mexus.homeleisure.common.DocsController;
import com.mexus.homeleisure.common.response.LinksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 컨트롤러
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/comments", produces = MediaTypes.HAL_JSON_VALUE)
public class CommentController {

  private final CommentService commentService;

  /**
   * 댓글 작성
   *
   * @param trainingId        트레이닝 ID
   * @param addCommentRequest 댓글 정보
   * @return API Docs 링크
   */
  @PostMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.CREATED)
  public LinksResponse addComment(
      @PathVariable Long trainingId,
      @RequestBody AddCommentRequest addCommentRequest
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    Long commentId = this.commentService.saveComment(trainingId, requestUserId, addCommentRequest);

    return new LinksResponse(
        linkTo(CommentController.class).slash(commentId).withSelfRel(),
        linkTo(DocsController.class).slash("#sendComment").withRel("profile")
    );
  }

  @GetMapping("/{trainingId}")
  @ResponseStatus(HttpStatus.OK)
  public PagedModel<CommentsResponse> getComments(
      @PathVariable Long trainingId,
      Pageable pageable,
      PagedResourcesAssembler<Comment> assembler
  ) {
    Page<Comment> comments = this.commentService.getComments(trainingId, pageable);
    PagedModel<CommentsResponse> commentsResponses =
        assembler.toModel(comments, comment -> new CommentsResponse(comment));
    commentsResponses.add(linkTo(DocsController.class).slash("#getComments").withRel("profile"));

    return commentsResponses;
  }


  /**
   * 댓글 수정
   *
   * @param commentId            트레이닝 ID
   * @param updateCommentRequest 댓글 정보
   * @return API Docs 링크
   */
  @PutMapping("/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  public LinksResponse updateComment(
      @PathVariable Long commentId,
      @RequestBody UpdateCommentRequest updateCommentRequest
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.commentService.updateComment(commentId, requestUserId, updateCommentRequest);

    return new LinksResponse(
        linkTo(CommentController.class).slash(commentId).withSelfRel(),
        linkTo(DocsController.class).slash("#updateComment").withRel("profile")
    );
  }

  /**
   * 댓글 삭제
   *
   * @param commentId 트레이닝 ID
   * @return API Docs 링크
   */
  @DeleteMapping("/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  public LinksResponse deleteComment(
      @PathVariable Long commentId
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.commentService.deleteComment(commentId, requestUserId);

    return new LinksResponse(
        linkTo(CommentController.class).slash(commentId).withSelfRel(),
        linkTo(DocsController.class).slash("#deleteComment").withRel("profile")
    );
  }
}

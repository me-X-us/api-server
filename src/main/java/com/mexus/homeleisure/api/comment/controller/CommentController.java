package com.mexus.homeleisure.api.comment.controller;

import com.mexus.homeleisure.api.common.DocsController;
import com.mexus.homeleisure.api.common.response.LinksResponse;
import com.mexus.homeleisure.api.comment.request.AddCommentRequest;
import com.mexus.homeleisure.api.training.controller.TrainingController;
import com.mexus.homeleisure.api.comment.request.UpdateCommentRequest;
import com.mexus.homeleisure.api.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * 댓글 컨트롤러
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/trainings/{trainingId}", produces = MediaTypes.HAL_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     *
     * @param trainingId            트레이닝 ID
     * @param addCommentRequest 댓글 정보
     * @return API Docs 링크
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LinksResponse addComment(
            @PathVariable Long trainingId,
            @RequestBody AddCommentRequest addCommentRequest
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        this.commentService.saveComment(trainingId, requestUserId, addCommentRequest);

        return new LinksResponse(
                linkTo(TrainingController.class).slash(trainingId).withSelfRel(),
                linkTo(DocsController.class).slash("#sendComment").withRel("profile")
        );
    }

    /**
     * 댓글 수정
     *
     * @param trainingId               트레이닝 ID
     * @param commentId            트레이닝 ID
     * @param updateCommentRequest 댓글 정보
     * @return API Docs 링크
     */
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public LinksResponse updateComment(
            @PathVariable Long trainingId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest updateCommentRequest
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        this.commentService.updateComment(trainingId, commentId, requestUserId, updateCommentRequest);

        return new LinksResponse(
                linkTo(TrainingController.class).slash(trainingId).withSelfRel(),
                linkTo(DocsController.class).slash("#updateComment").withRel("profile")
        );
    }

    /**
     * 댓글 삭제
     *
     * @param trainingId    트레이닝 ID
     * @param commentId 트레이닝 ID
     * @return API Docs 링크
     */
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public LinksResponse deleteComment(
            @PathVariable Long trainingId,
            @PathVariable Long commentId
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        this.commentService.deleteComment(trainingId, commentId, requestUserId);

        return new LinksResponse(
                linkTo(TrainingController.class).slash(trainingId).withSelfRel(),
                linkTo(DocsController.class).slash("#deleteComment").withRel("profile")
        );
    }
}

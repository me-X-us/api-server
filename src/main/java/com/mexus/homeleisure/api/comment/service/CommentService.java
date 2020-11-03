package com.mexus.homeleisure.api.comment.service;

import com.mexus.homeleisure.api.comment.data.Comment;
import com.mexus.homeleisure.api.comment.data.CommentRepository;
import com.mexus.homeleisure.api.comment.request.AddCommentRequest;
import com.mexus.homeleisure.api.comment.request.UpdateCommentRequest;
import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.api.training.data.TrainingRepository;
import com.mexus.homeleisure.api.training.exception.TrainingNotFoundException;
import com.mexus.homeleisure.api.user.data.Users;
import com.mexus.homeleisure.api.user.data.UsersRepository;
import com.mexus.homeleisure.api.user.exception.InvalidUserException;
import com.mexus.homeleisure.common.exception.ThisIsNotYoursException;
import com.mexus.homeleisure.security.data.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 서비스
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final TrainingRepository trainingRepository;
  private final UsersRepository usersRepository;

  /**
   * 댓글 입력
   *
   * @param trainingId        트레이닝 ID
   * @param addCommentRequest 댓글 작성 요청
   * @throws TrainingNotFoundException 존재하지 않는 트레이닝입니다.
   */
  @Transactional
  public Long saveComment(Long trainingId, String requestUserId,
                          AddCommentRequest addCommentRequest) {
    Training training = this.trainingRepository.findByTrainingId(trainingId)
        .orElseThrow(TrainingNotFoundException::new);
    Users author = this.usersRepository
        .findByUserIdAndState(requestUserId, UserStatus.NORMAL, Users.class)
        .orElseThrow(InvalidUserException::new);
    Comment comment = this.commentRepository.save(
        new Comment(
            author,
            addCommentRequest.getMessage(),
            training)
    );
    training.addComment(comment);
    return comment.getCommentId();
  }

  /**
   * 댓글 조회
   *
   * @param trainingId 트레이닝 ID
   * @param pageable   페이징 정보
   */
  @Transactional(readOnly = true)
  public Page<Comment> getComments(Long trainingId, Pageable pageable) {
    return this.commentRepository.findAllByTraining_TrainingId(trainingId, pageable);
  }

  /**
   * 댓글 수정
   *
   * @param commentId            댓글 ID
   * @param updateCommentRequest 댓글 수정 요청
   * @throws TrainingNotFoundException 존재하지 않는 트레이닝입니다.
   * @throws ThisIsNotYoursException   수정권한이 없습니다.
   */
  @Transactional
  public void updateComment(Long commentId, String requestUserId,
                            UpdateCommentRequest updateCommentRequest) {
    getMyComment(commentId, requestUserId).updateComment(updateCommentRequest.getMessage());
  }

  /**
   * 댓글 삭제
   *
   * @param commentId 댓글 ID
   * @throws TrainingNotFoundException 존재하지 않는 트레이닝입니다.
   * @throws ThisIsNotYoursException   수정권한이 없습니다.
   */
  @Transactional
  public void deleteComment(Long commentId, String requestUserId) {
    Comment comment = getMyComment(commentId, requestUserId);
    this.commentRepository.delete(comment);
  }

  /**
   * 내 댓글 가져오기
   *
   * @param commentId 댓글 ID
   * @return 댓글 엔터티
   * @throws ThisIsNotYoursException 수정권한이 없습니다.
   */
  public Comment getMyComment(Long commentId, String requestUserId) {
    return commentRepository.findByAuthor_UserIdAndCommentId(requestUserId, commentId)
        .orElseThrow(ThisIsNotYoursException::new);
  }
}

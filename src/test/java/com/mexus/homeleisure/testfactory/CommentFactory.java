package com.mexus.homeleisure.testfactory;

import com.mexus.homeleisure.api.comment.data.Comment;
import com.mexus.homeleisure.api.comment.data.CommentRepository;
import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.api.training.data.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommentFactory extends AccountFactory {

  @Autowired
  protected TrainingRepository trainingRepository;
  @Autowired
  protected CommentRepository commentRepository;


  @Transactional
  public long addComment(Training training, int i) {
    Comment savedComment = this.commentRepository.save(
        new Comment(
            generateUserAndGetUser(i),
            i + "번째 댓글",
            training
        )
    );
    training.addComment(savedComment);
    this.trainingRepository.save(training);
    return savedComment.getCommentId();
  }
}

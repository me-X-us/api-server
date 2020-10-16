package com.mexus.homeleisure.api.comment.dto;

import com.mexus.homeleisure.api.comment.data.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private String message;
    private String commenterId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CommentDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.message = comment.getMessage();
        this.commenterId = comment.getAuthor().getUserId();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}

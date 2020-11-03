package com.mexus.homeleisure.api.comment.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mexus.homeleisure.api.training.data.Training;
import com.mexus.homeleisure.common.Date;
import com.mexus.homeleisure.api.user.data.Users;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 댓글 엔터티
 *
 * @author always0ne
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "commentId", callSuper = false)
public class Comment extends Date {
    /**
     * pk
     */
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    /**
     * 댓글
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    /**
     * 댓글 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private Users author;

    @ManyToOne
    @JoinColumn(name = "training")
    private Training training;

    public Comment(Users author, String message, Training training) {
        super();
        this.author = author;
        this.message = message;
        this.training = training;
    }

    /**
     * 댓글 수정
     *
     * @param message 수정할 메시지
     */
    public void updateComment(String message) {
        this.message = message;
        this.updateModifyDate();
    }
}

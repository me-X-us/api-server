package com.mexus.homeleisure.testfactory;

import com.mexus.homeleisure.api.community.comment.data.Comment;
import com.mexus.homeleisure.api.community.comment.data.CommentRepository;
import com.mexus.homeleisure.api.community.post.data.Post;
import com.mexus.homeleisure.api.community.post.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommentFactory extends AccountFactory{

    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected CommentRepository commentRepository;


    @Transactional
    public long addComment(Post post, int i) {
        Comment savedComment = this.commentRepository.save(
                new Comment(
                        generateUserAndGetUser(i),
                        i + "번째 댓글"
                )
        );
        post.addComment(savedComment);
        this.postRepository.save(post);
        return savedComment.getCommentId();
    }
}

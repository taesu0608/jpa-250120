package com.example.jpa.domain.post.comment.service;

import com.example.jpa.domain.post.comment.entity.Comment;
import com.example.jpa.domain.post.comment.repository.CommentRepository;
import com.example.jpa.domain.post.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment write(Long postId, String body){
        Comment comment = Comment.builder()
                .body(body)
                .postId(postId)
                .build();

        return commentRepository.save(comment);
    }
}

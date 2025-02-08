package com.example.jpa.global;

import com.example.jpa.domain.post.comment.entity.Comment;
import com.example.jpa.domain.post.comment.service.CommentService;
import com.example.jpa.domain.post.post.entity.Post;
import com.example.jpa.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class BeanInitData {

    private final PostService postService;
    private final CommentService commentService;
    @Bean
    @Order(1)
    public ApplicationRunner applicationRunner() {
        return args -> {
            if (postService.count() > 0) {
                return;
            }
            // 샘플 데이터 3개 생성.
            Post p1 = postService.write("title1", "body1");
            Post p2 = postService.write("title2", "body3");
            Post p3 = postService.write("title3", "body3");

        };
    }

    @Bean
    @Order(2)
    public ApplicationRunner applicationRunner2() {
        return args -> {
          Comment c1 = commentService.write(1L,"comment");
          Comment c2 = commentService.write(1L,"comment");
          Comment c3 = commentService.write(1L,"comment");

        };
    }
}

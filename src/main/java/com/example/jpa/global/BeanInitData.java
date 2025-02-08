package com.example.jpa.global;

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
        /*return args -> {
            Post post = postService.findById(1L).get();
            postService.modify(post, "new", "new");
        };*/
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {
            Post post = postService.findById(1L).get();
            Thread.sleep(1000);
            postService.modify(post,"new","new");
            }
        };
    }

    @Bean
    @Order(3)
    public ApplicationRunner applicationRunner3() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Post post = postService.findById(1L).get();
                postService.delete(post);
                postService.deleteById(2L);
            }
        };
    }

    @Bean
    @Order(4)
    public ApplicationRunner applicationRunner4() {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {
                Post post = postService.findById(3L).get();
                System.out.println(post.getId()+"번 post 출력");
                Post post2 = postService.findById(3L).get();
                System.out.println(post2.getId()+"번 post 출력");

            }
        };
    }
}

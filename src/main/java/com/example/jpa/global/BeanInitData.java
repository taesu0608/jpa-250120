package com.example.jpa.global;

import com.example.jpa.domain.post.comment.entity.Comment;
import com.example.jpa.domain.post.comment.service.CommentService;
import com.example.jpa.domain.post.post.entity.Post;
import com.example.jpa.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class BeanInitData {

    private final PostService postService;
    private final CommentService commentService;
    @Autowired
    @Lazy
    private BeanInitData self;
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

            commentService.write(p1,"comment1");
            commentService.write(p1,"comment2");
            commentService.write(p1,"comment3");
            commentService.write(p2,"comment4");

        };
    }

    @Bean
    @Order(2)
    public ApplicationRunner applicationRunner2() {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {
                Comment c1 = commentService.findById(1L).get();
                Post post = c1.getPost();
            }
        };
    }

    @Bean
    @Order(3)
    public ApplicationRunner applicationRunner3() {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {
                Comment c1 = commentService.findById(1L).get();
                self.work1();
                self.work2();
                Post post = c1.getPost();
            }
        };
    }

    public void work1(){
        System.out.println("job 작업 실행");
        Comment c1 = commentService.findById(1L).get();
        Post post = c1.getPost();
        System.out.println(post.getClass()+"");
    }

    public void work2(){
        if (postService.count() > 0) {
            return;
        }

        Post p1 = postService.write("title1", "body1");

        Comment c1 = Comment.builder()
                .body("work2_comment1")
                .build();

        p1.addComment(c1);

        Comment c2 = Comment.builder()
                .body("work2_comment2")
                .build();

        p1.addComment(c2);

        Comment c3 = Comment.builder()
                .body("work2_comment3")
                .build();

        p1.addComment(c3);
        p1.removeComment(c1);
    }

    @Bean
    @Order(4)
    public ApplicationRunner applicationRunner4() {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {
                Comment c4 = new Comment().builder().body("comment4").build();
/*                1. commentService를 이용한 일대다 관계 객체 DB 영속화
                Post p2 = postService.findById(2L).get();
                commentService.write(p2,"comment4");*/
/*                2. 객체지향적 DB 영속화
                Post p2 = postService.findById(2L).get();
                commentService.save(c4);
                p2.addComment(c4);*/
                //3. commentSerive 사용 피하기
                // - commentSerivce() 호출로 영속성컨텍스트에 Comment객체를 등록하는 것이아닌 cascade를 활용하여 Post 객체 변경시 Comment 객체의 영속화가 이뤄지도록 변환
                Post p2 = postService.findById(2L).get();;
                p2.addComment(c4);

            }
        };
    }


}

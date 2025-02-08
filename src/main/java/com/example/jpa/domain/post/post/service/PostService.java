package com.example.jpa.domain.post.post.service;

import com.example.jpa.domain.post.post.entity.Post;
import com.example.jpa.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public Post write(String title, String body) {
        // 1. Post 조립
        Post post = new Post().builder()
                .title(title)
                .body(body).build();
//        post.setId(1L); id는 기본적으로 JPA가 관리함.
        // 2. repository에게 넘김 // 3. DB 반영
        postRepository.save(post);
        return post;
    }
    @Transactional
    public Post modify(Post post, String title, String body){
        post.setTitle(title);
        post.setBody(body);

        return post;
    }
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
    public long count(){
        return postRepository.count();
    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }
}

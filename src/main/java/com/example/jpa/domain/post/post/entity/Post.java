package com.example.jpa.domain.post.post.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.jpa.domain.post.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


//jpa는 jakarta를 통한 영속성 작업
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto_increment
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    @CreatedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime modifiedDate;

    @Column(length = 100)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
    public void removeComment (Comment c){
        comments.remove(c);
    }
    public void removeComment(long id) {
        Optional<Comment> opComment = comments.stream().filter(com->com.getId() == id)
                .findFirst();

        opComment.ifPresent(comment -> comments.remove(comment));
    }
}

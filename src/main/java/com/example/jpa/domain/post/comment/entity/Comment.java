package com.example.jpa.domain.post.comment.entity;

import com.example.jpa.domain.post.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//jpa는 jakarta를 통한 영속성 작업
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Comment {

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
    @ManyToOne
    private Post post;
    @Column(columnDefinition = "TEXT")
    private String body;
}

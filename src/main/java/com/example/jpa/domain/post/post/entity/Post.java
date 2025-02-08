package com.example.jpa.domain.post.post.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
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
    @LastModifiedBy
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime modifiedDate;
    @Column(length = 100)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
}

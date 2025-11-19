package com.teamgreen.makeplan.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 1대 N관계로 지연로딩으로 실제 필요한 순간까지 User를 DB에서 조회하지 않는다. Lazy를 사용하여 성능최적화가 실행됨. 예를
    // 들어List<Post> posts = postRepository.findAll();같은 상황이 발생하면 모든 Post의 User를 전부 SELECT하므로 느려지게됨
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // user가 삭제되면 포스트도 삭제된다
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}

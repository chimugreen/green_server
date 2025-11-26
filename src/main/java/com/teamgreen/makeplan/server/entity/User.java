package com.teamgreen.makeplan.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    private String profileImageUrl;

    @Column(length = 500)
    private String selfIntroduction;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getName() {
        return (name == null || name.isBlank()) ? "새로운 사용자" : name;
    }

    public String getProfileImageUrl() {
        return (profileImageUrl == null || profileImageUrl.isBlank()) ? "https://green-post-images.s3.amazonaws" +
                ".com/posts/e4db8837-212c-48f2-b714-c0979ed5d1f6_589119389_1149866060634771_6555316126677968055_n" +
                ".png":profileImageUrl;
    }

}

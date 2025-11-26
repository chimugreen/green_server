package com.teamgreen.makeplan.server.dto.post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String imageUrl;
    private Integer userId;
    private String username;
    private String profileImageUrl;
    private Integer likesCnt = 0;
    private Integer commentsCnt = 0;
}

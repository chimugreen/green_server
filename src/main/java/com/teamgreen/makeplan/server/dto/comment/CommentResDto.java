package com.teamgreen.makeplan.server.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResDto {
    private String id;
    private Integer userId;
    private String username;
    private String profileImageUrl;
    private String content;
    private LocalDateTime createdAt;
}

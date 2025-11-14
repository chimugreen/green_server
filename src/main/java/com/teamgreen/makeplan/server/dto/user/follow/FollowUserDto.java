package com.teamgreen.makeplan.server.dto.user.follow;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FollowUserDto {
    private Integer userId;
    private String name;
    private String profileImageUrl;
}

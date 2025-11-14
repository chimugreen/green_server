package com.teamgreen.makeplan.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileResDto {
    private String name;
    private String email;
    private Integer postCount;
    private Integer followerCount;
    private Integer followingCount;
    private Boolean isFollowing;
}

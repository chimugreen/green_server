package com.teamgreen.makeplan.server.dto.user.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class FollowListResDto {
    private List<FollowUserDto> list;
}

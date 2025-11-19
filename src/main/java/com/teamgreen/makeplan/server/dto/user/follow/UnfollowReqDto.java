package com.teamgreen.makeplan.server.dto.user.follow;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UnfollowReqDto {
    private final Integer userId;
}

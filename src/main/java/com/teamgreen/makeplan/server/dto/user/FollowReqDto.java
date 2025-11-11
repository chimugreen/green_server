package com.teamgreen.makeplan.server.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FollowReqDto {
    private final Integer userId;
}

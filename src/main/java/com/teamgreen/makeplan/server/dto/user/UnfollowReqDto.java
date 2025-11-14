package com.teamgreen.makeplan.server.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UnfollowReqDto {
    private final Integer userId;
}

package com.teamgreen.makeplan.server.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class UserPrincipal {
    private final Integer userId;
    private final String email;
}

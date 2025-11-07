package com.teamgreen.makeplan.server.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResDto {
    private final String accessToken;
}

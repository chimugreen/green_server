package com.teamgreen.makeplan.server.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignUpResDto {
    private final Integer id;
    private final String email;
}

package com.teamgreen.makeplan.server.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInReqDto {

    @NotEmpty(message = "사용자 email은 필수항목입니다.")
    @Email(message = "올바른 email형식이 아닙니다.")
    private final String email;

    @NotEmpty(message = "비밀번호는 필수 항목입니다")
    private final String password;
}

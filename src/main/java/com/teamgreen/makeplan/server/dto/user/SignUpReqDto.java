package com.teamgreen.makeplan.server.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpReqDto {

    @NotEmpty(message = "사용자 email은 필수항목입니다.")
    @Email(message = "올바른 email형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 항목입니다")
    private String password;

}

package com.teamgreen.makeplan.server.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthError implements Error {
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    INVALID_USER_INFO(HttpStatus.BAD_REQUEST, "유저 정보가 잘못되었습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}

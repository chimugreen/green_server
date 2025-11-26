package com.teamgreen.makeplan.server.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostError implements Error{
    NONE_EXISTS(HttpStatus.BAD_REQUEST, "포스트가 존재하지 않습니다");

    private final HttpStatus httpStatus;
    private final String message;
}

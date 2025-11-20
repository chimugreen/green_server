package com.teamgreen.makeplan.server.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoError implements Error {

    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 Todo가 존재하지 않습니다."),
    TODO_FORBIDDEN(HttpStatus.FORBIDDEN, "본인이 작성한 Todo만 접근할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
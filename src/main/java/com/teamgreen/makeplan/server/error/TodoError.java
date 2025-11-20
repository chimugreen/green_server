package com.teamgreen.makeplan.server.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoError implements Error {

    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 Todo가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
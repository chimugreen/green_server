package com.teamgreen.makeplan.server.error;

import org.springframework.http.HttpStatus;

public interface Error {
    String name();

    HttpStatus getHttpStatus();

    String getMessage();
}

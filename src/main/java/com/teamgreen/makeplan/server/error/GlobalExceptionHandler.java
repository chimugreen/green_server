package com.teamgreen.makeplan.server.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomExcep(RestApiException e) {
        return ResponseEntity.status(
                                     e.getError()
                                      .getHttpStatus()
                             )
                             .body(new ErrorResDto(e));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception e) {
        ErrorResDto dto = new ErrorResDto(e);
        return ResponseEntity
                .internalServerError()
                .body(dto);
    }
}

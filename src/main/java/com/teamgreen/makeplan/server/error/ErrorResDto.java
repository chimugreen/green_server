package com.teamgreen.makeplan.server.error;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Builder
public class ErrorResDto {

    private final String timestamp;
    private final String error;
    private final String message;

    public ErrorResDto(RestApiException error) {
        this.timestamp = LocalDateTime.now().toString();
        this.error = error.getError().name();
        this.message = error.getError().getMessage();
    }

    public ErrorResDto(Exception error) {
        this.timestamp = LocalDateTime.now().toString();
        this.error = error.getClass()
                          .getName();
        this.message = error.getMessage();
    }
}

package com.teamgreen.makeplan.server.dto.todo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTodoReqDto {
    private String content;
    private Boolean isDone;
    private LocalDateTime targetDate;
}

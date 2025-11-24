package com.teamgreen.makeplan.server.dto.todo;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
// Todo 수정 요청
public class UpdateTodoReqDto {
    @NotEmpty
    private String content;
    @NotEmpty
    private Boolean isDone;
}

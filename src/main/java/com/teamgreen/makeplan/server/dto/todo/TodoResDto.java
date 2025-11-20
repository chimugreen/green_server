package com.teamgreen.makeplan.server.dto.todo;

import com.teamgreen.makeplan.server.entity.Todo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TodoResDto {

    private Integer id;
    private String content;
    private boolean isDone;
    private LocalDateTime createDate;
    private LocalDateTime targetDate;

    //entity -> dto로 변환
    public static TodoResDto fromEntity(Todo todo) {
        return TodoResDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .isDone(todo.isDone())
                .createDate(todo.getCreateDate())
                .targetDate(todo.getTargetDate())
                .build();
    }
}

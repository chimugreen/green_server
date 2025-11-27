package com.teamgreen.makeplan.server.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teamgreen.makeplan.server.entity.Todo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TodoResDto { //Todo 목록 조회 응답

    private Integer id;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime schedule;

    private boolean isDone;

    //entity -> dto로 변환
    public static TodoResDto fromEntity(Todo todo) {
        return TodoResDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .schedule(todo.getSchedule())
                .isDone(todo.isDone())
                .build();
    }
}

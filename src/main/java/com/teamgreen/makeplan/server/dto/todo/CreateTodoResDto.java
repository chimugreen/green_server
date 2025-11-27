package com.teamgreen.makeplan.server.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teamgreen.makeplan.server.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
//response 응답 (Todo 생성 응답)
public class CreateTodoResDto {
    private final Integer id;

    private final String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime schedule;

    private boolean isDone;

    //entity 를 dto로 변환하기 메소드 (builder) 사용버전
    public static CreateTodoResDto fromEntity(Todo todo){
        return CreateTodoResDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .schedule(todo.getSchedule())
                .isDone(todo.isDone())
                .build();
    }


}

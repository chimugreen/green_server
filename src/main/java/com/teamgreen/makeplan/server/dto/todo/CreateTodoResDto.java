package com.teamgreen.makeplan.server.dto.todo;

import com.teamgreen.makeplan.server.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
//response 응답
public class CreateTodoResDto {
    private final Integer id;
    private final String content;
    private boolean isDone;
    private LocalDateTime targetDate;

    //entity 를 dto로 변환하기 메소드 보디(bulder) 사용버전
    public static CreateTodoResDto fromEntity(Todo todo){
        return CreateTodoResDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .isDone(todo.isDone())
                .targetDate(todo.getTargetDate())
                .build();
    }


}

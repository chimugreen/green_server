package com.teamgreen.makeplan.server.dto.todo;

import com.teamgreen.makeplan.server.entity.Todo;
import com.teamgreen.makeplan.server.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
//request 요청
public class TodoReqDto {

    private boolean isDone;

    @NotEmpty(message = "할 일을 넣으세요")
    private String content;

    private LocalDateTime targetDate;


    public Todo toEntity(User writer){
        return Todo.builder()
                .content(this.content)
                .targetDate(this.targetDate)
                .writer(writer)
                .isDone(false)
                .build();
    }

}

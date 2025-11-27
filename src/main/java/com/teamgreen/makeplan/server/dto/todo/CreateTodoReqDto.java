package com.teamgreen.makeplan.server.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teamgreen.makeplan.server.entity.Todo;
import com.teamgreen.makeplan.server.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
//request 요청 (Todo 생성 요청)
public class CreateTodoReqDto {

    private boolean isDone;

    @NotEmpty(message = "할 일을 넣으세요")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime schedule;

    //dto - entity로 변환
    public Todo toEntity(User writer){
        return Todo.builder()
                .content(this.content)
                .writer(writer)
                .isDone(false)
                .schedule(this.schedule)
                .build();
    }

}

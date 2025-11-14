package com.teamgreen.makeplan.server.controller;

import com.teamgreen.makeplan.server.auth.TokenStore;
import com.teamgreen.makeplan.server.dto.todo.TodoReqDto;
import com.teamgreen.makeplan.server.dto.todo.CreateTodoResDto;
import com.teamgreen.makeplan.server.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

        private final TodoService todoService;
        private final TokenStore tokenStore;

        // Todo 생성
        @PostMapping
        public CreateTodoResDto createTodo(
                @RequestBody TodoReqDto todoReqDto,
                @RequestHeader("Authorization") String authorizationHeader
        ) {
                // 🔥 Authorization: "Bearer eyJxxxxx"
                String accessToken = authorizationHeader.replace("Bearer ", "").trim();

                String email = tokenStore.getEmailByToken(accessToken);

                return todoService.createTodo(todoReqDto.getContent(), email);
        }

        // Todo 목록 조회 (로그인한 유저만)
        @GetMapping
        public List<CreateTodoResDto> getTodosByEmail(
                @RequestHeader("Authorization") String authorizationHeader
        ) {
                String accessToken = authorizationHeader.replace("Bearer ", "").trim();
                String email = tokenStore.getEmailByToken(accessToken);

                return todoService.getTodosByEmail(email);
        }

        // Todo 삭제
        @DeleteMapping("/{id}")
        public void deleteTodo(
                @PathVariable Integer id,
                @RequestHeader("Authorization") String authorizationHeader
        ) {
                String accessToken = authorizationHeader.replace("Bearer ", "").trim();
                String email = tokenStore.getEmailByToken(accessToken);

                todoService.deleteTodo(id, email);
        }

        // Todo 수정 (나중에 구현)
}

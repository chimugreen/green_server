package com.teamgreen.makeplan.server.controller;

import com.teamgreen.makeplan.server.auth.TokenStore;
import com.teamgreen.makeplan.server.dto.todo.TodoReqDto;
import com.teamgreen.makeplan.server.dto.todo.CreateTodoResDto;
import com.teamgreen.makeplan.server.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
@RestController
public class TodoController {

        private final TodoService todoService;
        private final TokenStore tokenStore;

        //Todo 생성 (post 요청 처리)
        @PostMapping
        public CreateTodoResDto createTodo(@RequestBody TodoReqDto todoReqDto, @RequestHeader("Authorization") String authorizationHeader){
                String accessToken = authorizationHeader.replace("Bearer ", "");
                String email = tokenStore.getEmailByToken(accessToken);

                return todoService.createTodo(todoReqDto.getContent(), email);
        }

        //Todo 전체 조회 (Get 요청 처리 메소드) , 로그인 한 사람들꺼만
        @GetMapping
        public List<CreateTodoResDto> getTodosByEmail(@RequestHeader("Authorization") String authorizationHeader){
                String accessToken = authorizationHeader.replace("Bearer ", "");
                String email = tokenStore.getEmailByToken(accessToken);

                return todoService.getTodosByEmail(email);
        }


        //Todo 삭제
        @DeleteMapping("/{id}")
        public void deleteTodo(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader) {
                String accessToken = authorizationHeader.replace("Bearer ", "");
                String email = tokenStore.getEmailByToken(accessToken);

                todoService.deleteTodo(id, email);
        }




        //Todo 수정
}

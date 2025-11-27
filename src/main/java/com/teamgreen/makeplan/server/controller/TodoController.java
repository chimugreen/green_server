package com.teamgreen.makeplan.server.controller;

import com.teamgreen.makeplan.server.auth.UserPrincipal;
import com.teamgreen.makeplan.server.base.BaseController;
import com.teamgreen.makeplan.server.dto.todo.CreateTodoReqDto;
import com.teamgreen.makeplan.server.dto.todo.CreateTodoResDto;
import com.teamgreen.makeplan.server.dto.todo.TodoResDto;
import com.teamgreen.makeplan.server.dto.todo.UpdateTodoReqDto;
import com.teamgreen.makeplan.server.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TodoController extends BaseController {

        private final TodoService todoService;

        // Todo 생성
        @PostMapping
        public CreateTodoResDto createTodo(@Valid @RequestBody CreateTodoReqDto createTodoReqDto) { //res 반환 req 로 받음

                UserPrincipal currentUser = getCurrentUser();
                Integer userId = currentUser.getUserId();


                return todoService.createTodo(createTodoReqDto, userId);
        }

        // Todo 목록 조회 (로그인한 유저만)
        @GetMapping
        public List<TodoResDto> getTodos() {

                UserPrincipal currentUser = getCurrentUser();
                Integer userId = currentUser.getUserId();

                return todoService.getTodosByUserId(userId);
        }

        // Todo 삭제
        @DeleteMapping("/{id}")
        public void deleteTodo(@PathVariable Integer id) {

                UserPrincipal currentUser = getCurrentUser();
                Integer userId = currentUser.getUserId();

                todoService.deleteTodo(id, userId);
        }

        // Todo 수정
        @PutMapping("/{id}")
        public CreateTodoResDto updateTodo(
                @PathVariable Integer id,
                @Valid @RequestBody UpdateTodoReqDto updateTodoReqDto) {

                UserPrincipal currentUser = getCurrentUser();
                Integer userId = currentUser.getUserId();

                return todoService.updateTodo(id, userId, updateTodoReqDto);
        }


}

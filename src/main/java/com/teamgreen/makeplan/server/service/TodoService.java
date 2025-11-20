package com.teamgreen.makeplan.server.service;

import com.teamgreen.makeplan.server.dto.todo.CreateTodoResDto;
import com.teamgreen.makeplan.server.dto.todo.TodoReqDto;
import com.teamgreen.makeplan.server.entity.Todo;
import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.error.AuthError;
import com.teamgreen.makeplan.server.error.RestApiException;
import com.teamgreen.makeplan.server.error.TodoError;
import com.teamgreen.makeplan.server.repository.TodoRepository;
import com.teamgreen.makeplan.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository; // DB 접근 담당
    private final UserRepository userRepository;

    // Todo 생성
    public CreateTodoResDto createTodo(String content, Integer userId) {

        //에러 처리 : 존재하지 않는 유저일 경우 AuthError 반환
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(AuthError.INVALID_USER_INFO));

        Todo todo = Todo.builder()
                .content(content)
                .writer(user)
                .build();

        todoRepository.save(todo);

        return CreateTodoResDto.fromEntity(todo);
    }


    // Todo 목록 조회
    public List<CreateTodoResDto> getTodosByUserId(Integer userId) {

        //작성자(userId) 기준으로 Todo 목록 조회
        List<Todo> todos = todoRepository.findByWriterId(userId);

        return todos.stream()
                .map(CreateTodoResDto::fromEntity)
                .toList();
    }


    // Todo 수정
    public CreateTodoResDto updateTodo(Integer id, Integer userId, TodoReqDto dto) {

        //에러 처리: 잘못된 유저 정보일 경우 AuthError
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(AuthError.INVALID_USER_INFO));

        //에러 처리: 존재하지 않는 Todo일 경우 TodoError 반환
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RestApiException(TodoError.TODO_NOT_FOUND));

        //에러 처리 : 본인이 작성한 Todo가 아닐 경우 권한 없음
        if (!todo.getWriter().equals(user)) {
            throw new RestApiException(TodoError.TODO_FORBIDDEN);
        }

        // 필드 업데이트
        if (dto.getContent() != null) {
            todo.setContent(dto.getContent());
        }
        todo.setDone(dto.isDone());
        todo.setTargetDate(dto.getTargetDate());

        todoRepository.save(todo);

        return CreateTodoResDto.fromEntity(todo);
    }


    // Todo 삭제
    public void deleteTodo(Integer id, Integer userId) {

        //유저 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(AuthError.INVALID_USER_INFO));

        //삭제할 Todo 존재 여부 확인
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RestApiException(TodoError.TODO_NOT_FOUND));

        //작성자 본인이 아닐 경우 삭제 불가
        if (!todo.getWriter().equals(user)) {
            throw new RestApiException(TodoError.TODO_FORBIDDEN);
        }

        todoRepository.delete(todo);
    }

}

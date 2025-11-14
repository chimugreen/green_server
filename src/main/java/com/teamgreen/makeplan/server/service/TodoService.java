package com.teamgreen.makeplan.server.service;

import com.teamgreen.makeplan.server.dto.todo.CreateTodoResDto;
import com.teamgreen.makeplan.server.entity.Todo;
import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.repository.TodoRepository;
import com.teamgreen.makeplan.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository; //db 접근 담당
    private final UserRepository userRepository;

    //Todo 생성
    public CreateTodoResDto createTodo(String content, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        Todo todo = Todo.builder()
                .content(content)
                .writer(user)
                .build();

        todoRepository.save(todo);

        return CreateTodoResDto.fromEntity(todo);
    }

    //Todo 목록 조회
    public List<CreateTodoResDto> getTodosByEmail(String email) { //Todo 목록을 DTO형태로 반환
        // DB에서 email로 User를 조회 (없으면 예외 발생)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다"));
        // 이메일 기반으로 Todo 목록 조회
        List<Todo> todos = todoRepository.findByWriterEmail(email);
        // 엔티티 리스트 -> DTO 리스트로 변환
        return todos.stream()
                .map(CreateTodoResDto::fromEntity)
                .toList();
    }

//
//    //Todo 수정
//    public CreateTodoResDto updateTodo(Integer id , String email, UpdateTodoReqDto dto){
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));
//        Todo todo = todoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException()"수정한 내용이 없습니다");
//
//        }
//    }

    //Todo 삭제
    public void deleteTodo(Integer id ,String email){
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));
        Todo todo = todoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("삭제할 Todo가 없습니다."));
        if (!todo.getWriter().equals(user)){
            throw new RuntimeException("본인이 작성한 Todo만 삭제할 수 있습니다");
        }

        todoRepository.deleteById(id); //위 로직 통과 후 삭제 완료
    }



}

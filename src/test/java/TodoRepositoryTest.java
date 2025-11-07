package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.Todo;
import com.teamgreen.makeplan.server.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("1️⃣ 단일 Todo 삭제 테스트 - 정상적으로 삭제되는지 확인")
    void singleTodoDeleteTest() {
        // given
        User user = new User();
        user.setEmail("delete1@test.com");
        user.setPassword("1234");
        user.setName("삭제유저");

        userRepository.save(user);

        Todo todo = new Todo();
        todo.setContent("삭제 테스트용 Todo");
        todo.setTargetDate(LocalDateTime.now().plusDays(1));
        todo.setWriter(user);

        todoRepository.save(todo);
        todoRepository.flush();

        // when
        List<Todo> before = todoRepository.findAll();
        System.out.println("📝 삭제 전 Todo 개수: " + before.size());
        assertThat(before).hasSize(1);

        todoRepository.delete(todo);
        todoRepository.flush();

        // then
        List<Todo> after = todoRepository.findAll();
        System.out.println("🗑️ 삭제 후 Todo 개수: " + after.size());
        assertThat(after).isEmpty();
    }

    @Test
    @DisplayName("2️⃣ 여러 Todo 중 일부만 삭제 테스트 - 개별 삭제 확인")
    void multipleTodoPartialDeleteTest() {
        // given
        User user = new User();
        user.setEmail("multi_delete@test.com");
        user.setPassword("5678");
        user.setName("다중삭제유저");

        userRepository.save(user);

        // Todo 여러 개 추가
        for (int i = 1; i <= 3; i++) {
            Todo todo = new Todo();
            todo.setContent("삭제할 Todo " + i);
            todo.setTargetDate(LocalDateTime.now().plusDays(i));
            todo.setWriter(user);
            todoRepository.save(todo);
        }
        todoRepository.flush();

        List<Todo> all = todoRepository.findAll();
        System.out.println("📝 초기 Todo 개수: " + all.size());
        assertThat(all).hasSize(3);

        // 첫 번째 Todo만 삭제
        Todo firstTodo = all.get(0);
        todoRepository.delete(firstTodo);
        todoRepository.flush();

        // then
        List<Todo> remaining = todoRepository.findAll();
        System.out.println("🗑️ 일부 삭제 후 Todo 개수: " + remaining.size());
        assertThat(remaining).hasSize(2);
    }
}

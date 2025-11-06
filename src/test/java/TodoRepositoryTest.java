package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.Todo;
import com.teamgreen.makeplan.server.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveTodo() {
        User user = userRepository.save(
                User.builder()
                        .email("test@test.com")
                        .password("1234")
                        .name("테스트유저")
                        .build()
        );

        Todo todo = new Todo();
        todo.setContent("MySQL 기반 테스트");
        todo.setDone(false);
        todo.setTargetDate(LocalDateTime.now().plusDays(1));
        todo.setWriter(user);

        todoRepository.save(todo);
    }
}

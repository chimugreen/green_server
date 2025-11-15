package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    // writer_id 기준으로 Todo 목록 조회
    List<Todo> findByWriterId(Integer writerId);

    // writer.email 기준으로 Todo 목록 조회
    List<Todo> findByWriterEmail(String email);

}

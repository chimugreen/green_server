package com.teamgreen.makeplan.server.repository;


import com.teamgreen.makeplan.server.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

}

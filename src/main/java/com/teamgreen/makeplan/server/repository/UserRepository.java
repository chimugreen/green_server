package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// User 엔티티용 JPA 리포지토리
public interface UserRepository extends JpaRepository<User, Integer> {
}

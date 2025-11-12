package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.entity.user.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}

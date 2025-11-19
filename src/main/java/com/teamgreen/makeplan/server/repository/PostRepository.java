package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

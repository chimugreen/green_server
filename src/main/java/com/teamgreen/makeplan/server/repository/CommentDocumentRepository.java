package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.document.comment.CommentDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDocumentRepository extends MongoRepository<CommentDocument, String> {
    Page<CommentDocument> findAllByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);
}

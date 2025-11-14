package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.document.user.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserDocumentRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findById(Integer id);
}

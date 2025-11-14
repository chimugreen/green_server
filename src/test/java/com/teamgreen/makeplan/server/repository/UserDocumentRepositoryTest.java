package com.teamgreen.makeplan.server.repository;

import com.teamgreen.makeplan.server.entity.document.user.UserDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class UserDocumentRepositoryTest {
    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Test
    void baseTest() {

        UserDocument userDocument = UserDocument
                .builder()
                .id(1)
                .build();
        userDocumentRepository.save(userDocument);

        List<UserDocument> all = userDocumentRepository.findAll();
        System.out.println("all = " + all);
    }
}

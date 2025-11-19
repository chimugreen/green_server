package com.teamgreen.makeplan.server.service;

import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.repository.S3Repository;
import com.teamgreen.makeplan.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockitoBean
    private S3Repository s3Repository;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void createPostTest() throws Exception {

        // 1) S3 업로드 Mock
        given(s3Repository.upload(any(), anyString()))
                .willReturn("mock-image-url");

        // 2) User Mock
        User mockUser = User.builder()
                            .id(1)
                            .email("test@test.com")
                            .password("1234")
                            .build();

        given(userRepository.findById(1))
                .willReturn(Optional.of(mockUser));

        // 3) 테스트용 이미지 읽기
        InputStream inputStream = getClass().getClassLoader()
                                            .getResourceAsStream("test-image.webp");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.webp",
                "image/webp",
                inputStream
        );

        // 4) 실제 PostService 로직 호출
        Long postId = postService.createPost(1, file, "테스트 포스트");

        System.out.println("생성된 POST ID = " + postId);
    }
}
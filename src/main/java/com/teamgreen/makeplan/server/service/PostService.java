package com.teamgreen.makeplan.server.service;

import com.teamgreen.makeplan.server.entity.Post;
import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.error.AuthError;
import com.teamgreen.makeplan.server.error.CommonError;
import com.teamgreen.makeplan.server.error.RestApiException;
import com.teamgreen.makeplan.server.repository.PostRepository;
import com.teamgreen.makeplan.server.repository.S3Repository;
import com.teamgreen.makeplan.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final S3Repository s3Repository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(Integer userId, MultipartFile file, String content) throws Exception {
        String uploadedUrl = null;

        if (file != null && !file.isEmpty()) {
            uploadedUrl = s3Repository.upload(file, "posts");
        } else {
            throw new RestApiException(CommonError.EMPTY_FILE);
        }

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RestApiException(AuthError.INVALID_USER_INFO));

        Post post = Post
                .builder()
                .user(user)
                .content(content)
                .imageUrl(uploadedUrl)
                .build();

        postRepository.save(post);

        return post.getId();
    }
}

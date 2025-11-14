package com.teamgreen.makeplan.server.service;

import com.teamgreen.makeplan.server.auth.JwtTokenProvider;
import com.teamgreen.makeplan.server.auth.TokenStore;
import com.teamgreen.makeplan.server.dto.user.UserProfileResDto;
import com.teamgreen.makeplan.server.dto.user.follow.FollowUserDto;
import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.entity.document.user.UserDocument;
import com.teamgreen.makeplan.server.error.AuthError;
import com.teamgreen.makeplan.server.error.RestApiException;
import com.teamgreen.makeplan.server.repository.UserDocumentRepository;
import com.teamgreen.makeplan.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDocumentRepository userDocumentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenStore tokenStore;

    public UserProfileResDto getProfile(Integer requesterId, Integer userId) {
        User user = getUserFromSql(userId);
        UserDocument userDocument = getUserDocument(userId);
        UserDocument requesterDocument = getUserDocument(requesterId);

        boolean isFollowing = userDocument.getFollowers()
                                          .contains(requesterId);

        return new UserProfileResDto(
                user.getName(),
                user.getEmail(),
                0,
                userDocument.getFollowers()
                            .size(),
                userDocument.getFollowing()
                            .size(),
                isFollowing
                );

    }

    @Transactional
    public User create(User user) {
        // 중복 확인
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RestApiException(AuthError.DUPLICATE_EMAIL);
        }

        return this.userRepository.save(user);
    }

    @Transactional
    public String login(String email, String password) {

        User savedUser = getUserFromSql(email);

        if (!savedUser.getPassword()
                      .equals(password)) {
            throw new RestApiException(AuthError.INVALID_USER_INFO);
        }

        String token = jwtTokenProvider.buildJwtToken(savedUser.getId(), savedUser.getEmail());
        tokenStore.save(savedUser.getEmail(), token);
        return token;
    }


    /**
     * Follow.
     *
     * @param requesterId 요청을 한 사람
     * @param targetId    팔로우를 받을 사람
     */
    @Transactional
    public void follow(Integer requesterId, Integer targetId) {
        UserDocument requester = getUserDocument(requesterId);

        UserDocument target = getUserDocument(targetId);

        if (!requester.getFollowing()
                      .contains(targetId)) {
            requester.getFollowing()
                     .add(targetId);
        }
        if (!target.getFollowers()
                   .contains(requesterId)) {
            target.getFollowers()
                  .add(requesterId);
        }

        userDocumentRepository.save(requester);
        userDocumentRepository.save(target);
    }

    public List<FollowUserDto> followers(Integer userId) {
        List<FollowUserDto> list = new ArrayList<>();

        getUserDocument(userId).getFollowers()
                               .forEach(id -> {
                                   User userFromSql = getUserFromSql(id);
                                   list.add(new FollowUserDto(
                                           id,
                                           userFromSql.getName(),
                                           userFromSql.getProfileImageUrl()
                                   ));
                               });

        return list;
    }

    public List<FollowUserDto> followings(Integer userId) {
        List<FollowUserDto> list = new ArrayList<>();

        getUserDocument(userId).getFollowing()
                               .forEach(id -> {
                                   User userFromSql = getUserFromSql(id);
                                   list.add(new FollowUserDto(
                                           id,
                                           userFromSql.getName(),
                                           userFromSql.getProfileImageUrl()
                                   ));
                               });

        return list;
    }

    /**
     * Unfollow.
     *
     * @param requesterId 요청을 한 사람
     * @param targetId    언팔로우 할 대상
     */
    @Transactional
    public void unfollow(Integer requesterId, Integer targetId) {
        userDocumentRepository.findById(requesterId)
                              .ifPresent(r -> {
                                  r.getFollowing()
                                   .remove(targetId);
                                  userDocumentRepository.save(r);
                              });

        userDocumentRepository.findById(targetId)
                              .ifPresent(t -> {
                                  t.getFollowers()
                                   .remove(requesterId);

                                  userDocumentRepository.save(t);
                              });
    }

    public List<Integer> getFollowing(Integer userId) {
        return userDocumentRepository.findById(userId)
                                     .map(UserDocument::getFollowing)
                                     .orElse(List.of());
    }

    public List<Integer> getFollowers(Integer userId) {
        return userDocumentRepository.findById(userId)
                                     .map(UserDocument::getFollowers)
                                     .orElse(List.of());
    }


    private User getUserFromSql(Integer userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new RestApiException(AuthError.INVALID_USER_INFO));
    }

    private User getUserFromSql(String email) {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new RestApiException(AuthError.INVALID_USER_INFO));
    }

    private UserDocument getUserDocument(Integer userId) {
        return userDocumentRepository.findById(userId)
                                     .orElseGet(() -> userDocumentRepository.save(UserDocument.builder()
                                                                                              .id(userId)
                                                                                              .build()));
    }

}

package com.teamgreen.makeplan.server.service;

import com.teamgreen.makeplan.server.auth.JwtTokenProvider;
import com.teamgreen.makeplan.server.auth.TokenStore;
import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenStore tokenStore;

    @Transactional
    public User create(User user) {

        // 중복 확인
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        return this.userRepository.save(user);
    }

    @Transactional
    public String login(String email, String password) {

        User savedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!savedUser.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtTokenProvider.buildJwtToken(email);
        tokenStore.save(savedUser.getEmail(), token);
        return token;
    }

}

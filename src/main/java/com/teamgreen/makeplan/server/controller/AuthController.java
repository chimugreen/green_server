package com.teamgreen.makeplan.server.controller;

import com.teamgreen.makeplan.server.dto.auth.SignInReqDto;
import com.teamgreen.makeplan.server.dto.auth.SignInResDto;
import com.teamgreen.makeplan.server.dto.user.SignUpReqDto;
import com.teamgreen.makeplan.server.dto.user.SignUpResDto;
import com.teamgreen.makeplan.server.entity.User;
import com.teamgreen.makeplan.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 회원가입 메서드
     */
    @PostMapping("/signup")
    public SignUpResDto signUp(@Valid @RequestBody SignUpReqDto dto) {

        User user = User
                .builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        User createdUser = userService.create(user);

        return new SignUpResDto(createdUser.getId(), createdUser.getEmail());
    }


    /**
     * 로그인 메서드
     * @return accessToken
     */
    @PostMapping("/signin")
    public SignInResDto signIn(@Valid @RequestBody SignInReqDto dto) {
        String accessToken = userService.login(dto.getEmail(), dto.getPassword());
        return new SignInResDto(accessToken);
    }
}

package com.teamgreen.makeplan.server.controller;

import com.teamgreen.makeplan.server.dto.user.FollowReqDto;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/follow")
    public void followUser(@Valid @RequestBody FollowReqDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // JwtAuthenticationFilter에서 넣어준 email
        String email = (String) authentication.getPrincipal();

        System.out.println("\"현재 로그인한 사용자: \" + email = " + "현재 로그인한 사용자: " + email);

        System.out.println("dto = " + dto);

        return;
    }
}

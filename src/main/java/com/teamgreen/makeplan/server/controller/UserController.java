package com.teamgreen.makeplan.server.controller;

import com.teamgreen.makeplan.server.auth.UserPrincipal;
import com.teamgreen.makeplan.server.base.BaseController;
import com.teamgreen.makeplan.server.dto.user.FollowReqDto;
import com.teamgreen.makeplan.server.dto.user.UnfollowReqDto;

import com.teamgreen.makeplan.server.dto.user.UserProfileResDto;
import com.teamgreen.makeplan.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserProfileResDto getUserProfile(@PathVariable Integer userId) {
        UserPrincipal currentUser = getCurrentUser();
        return
        userService.getProfile(currentUser.getUserId(), userId);
    }

    @PostMapping("/follow")
    public void followUser(@Valid @RequestBody FollowReqDto dto) {

        UserPrincipal currentUser = getCurrentUser();

        userService.follow(currentUser.getUserId(), dto.getUserId());
    }

    @PostMapping("/unfollow")
    public void unfollowUser(@Valid @RequestBody UnfollowReqDto dto) {
        UserPrincipal currentUser = getCurrentUser();

        userService.unfollow(currentUser.getUserId(), dto.getUserId());
    }
}
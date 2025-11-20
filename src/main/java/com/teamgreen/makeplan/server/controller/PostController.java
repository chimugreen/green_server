package com.teamgreen.makeplan.server.controller;

import com.teamgreen.makeplan.server.auth.UserPrincipal;
import com.teamgreen.makeplan.server.base.BaseController;
import com.teamgreen.makeplan.server.dto.post.PostCreateReqDto;
import com.teamgreen.makeplan.server.dto.post.PostCreateResDto;
import com.teamgreen.makeplan.server.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController extends BaseController {
    private final PostService postService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostCreateResDto createPost(@ModelAttribute PostCreateReqDto req) throws Exception {

        UserPrincipal currentUser = getCurrentUser();

        Long postId = postService.createPost(
                currentUser.getUserId(),
                req.getFile(),
                req.getContent()
        );

        return new PostCreateResDto(postId);
    }
}

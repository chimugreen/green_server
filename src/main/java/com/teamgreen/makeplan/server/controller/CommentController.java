package com.teamgreen.makeplan.server.controller;

import com.teamgreen.makeplan.server.auth.UserPrincipal;
import com.teamgreen.makeplan.server.base.BaseController;
import com.teamgreen.makeplan.server.dto.comment.CreateCommentReqDto;
import com.teamgreen.makeplan.server.dto.comment.GetCommentReqDto;
import com.teamgreen.makeplan.server.dto.comment.GetCommentResDto;
import com.teamgreen.makeplan.server.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController extends BaseController {

    private final CommentService commentService;

    @PostMapping("")
    public void createComment(@Valid @RequestBody CreateCommentReqDto dto) {

        UserPrincipal currentUser = getCurrentUser();

        commentService.createComment(currentUser.getUserId(), dto);
    }

    @PostMapping("/list")
    public GetCommentResDto getComment(@Valid @RequestBody GetCommentReqDto dto) {
        UserPrincipal currentUser = getCurrentUser();
        return commentService.getComments(dto.getPostId(), dto.getPage(), dto.getSize());
    }
}

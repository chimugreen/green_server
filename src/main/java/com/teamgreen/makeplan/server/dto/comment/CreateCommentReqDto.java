package com.teamgreen.makeplan.server.dto.comment;

import lombok.Data;

@Data
public class CreateCommentReqDto {
    private Long postId;
    private String content;
}

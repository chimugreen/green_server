package com.teamgreen.makeplan.server.dto.comment;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetCommentReqDto {
    @NonNull
    private Long postId;
    private Integer page = 0;
    private Integer size = 10;
}

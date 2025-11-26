package com.teamgreen.makeplan.server.dto.comment;

import com.teamgreen.makeplan.server.dto.common.PagenationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class GetCommentResDto {
    private List<CommentResDto> comments;
    private PagenationDto pagenationDto;
}

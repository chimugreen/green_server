package com.teamgreen.makeplan.server.dto.post;

import com.teamgreen.makeplan.server.dto.common.PagenationDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPostListResDto {
    private List<PostResDto> posts;
    private PagenationDto pagenation;
}

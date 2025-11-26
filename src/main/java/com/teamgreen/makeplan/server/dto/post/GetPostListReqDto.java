package com.teamgreen.makeplan.server.dto.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GetPostListReqDto {
    private Integer userId;
    private Integer page = 0;
    private Integer size = 10;
}

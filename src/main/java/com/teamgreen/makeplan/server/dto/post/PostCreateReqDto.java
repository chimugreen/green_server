package com.teamgreen.makeplan.server.dto.post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateReqDto {
    private MultipartFile file;
    private String content;
}

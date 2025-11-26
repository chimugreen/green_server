package com.teamgreen.makeplan.server.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagenationDto {
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
    private boolean hasNext;
}

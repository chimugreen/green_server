package com.teamgreen.makeplan.server.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EditNameReqDto {
    @NotEmpty
    private String name;
}

package com.forpawchain.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResDto {
    private int uid;
    private String name;
    private String pfofile;
}

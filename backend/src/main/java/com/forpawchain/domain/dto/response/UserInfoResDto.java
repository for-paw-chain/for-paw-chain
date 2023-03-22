package com.forpawchain.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResDto {
	private Long uid;
	private String profile;
	private String name;
	private Boolean doctor;
}

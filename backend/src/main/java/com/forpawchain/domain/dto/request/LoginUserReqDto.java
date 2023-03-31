package com.forpawchain.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserReqDto {
	private String id;
	private String social;
}
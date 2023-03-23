package com.forpawchain.domain.dto.request;

import com.forpawchain.domain.Entity.Social;

import lombok.Data;

@Data
public class LoginUserReqDto {
	private String id;
	private String social;
}

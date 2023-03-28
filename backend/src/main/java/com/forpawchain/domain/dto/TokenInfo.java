package com.forpawchain.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {
	private String grantType; // Bearer 고정
	private String accessToken;
	private String refreshToken;
}
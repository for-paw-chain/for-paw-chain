package com.forpawchain.domain.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserDto {
	private String id;
	private String social;
}
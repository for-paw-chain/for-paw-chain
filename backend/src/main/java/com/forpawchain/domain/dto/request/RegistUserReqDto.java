package com.forpawchain.domain.dto.request;

import com.forpawchain.domain.entity.Social;
import com.forpawchain.domain.entity.UserEntity;

import lombok.Data;

@Data
public class RegistUserReqDto {
	private String id;
	private Social social;
	private String name;
	private String profile;

	public UserEntity toEntity() {
		return UserEntity.builder()
			.id(id)
			.social(social)
			.name(name)
			.profile(profile)
			.build();
	}
}

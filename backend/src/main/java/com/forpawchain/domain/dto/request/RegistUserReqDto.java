package com.forpawchain.domain.dto.request;

import com.forpawchain.domain.Entity.Social;
import com.forpawchain.domain.Entity.UserEntity;

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
			.sociasl(social)
			.name(name)
			.profile(profile)
			.build();
	}
}

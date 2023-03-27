package com.forpawchain.domain.dto.response;

import com.forpawchain.domain.Entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResDto {
	private Long uid;
	private String profile;
	private String name;
	private Boolean doctor;

	public UserInfoResDto(UserEntity userEntity) {
		this.uid = userEntity.getUid();
		this.profile = userEntity.getProfile();
		this.name = userEntity.getName();
		this.doctor = false;
	}
}

package com.forpawchain.domain.dto.response;

import com.forpawchain.domain.Entity.UserEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserInfoResDto", description = "반환되는 회원 정보 (회원 프로필)")
public class UserInfoResDto {
	@ApiModelProperty(name = "uid", value = "사용자 고유 번호", required = true, example = "1")
	private Long uid;

	@ApiModelProperty(name = "profile", value = "이미지 저장 URL", required = false, example = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Labrador-retriever.jpg/1200px-Labrador-retriever.jpg")
	private String profile;

	@ApiModelProperty(name = "name", value = "회원 이름", required = true, example = "최준아")
	private String name;

	@ApiModelProperty(name = "doctor", value = "의사 인증 여부", required = true, example = "false")
	private Boolean doctor;

	public UserInfoResDto(UserEntity userEntity) {
		this.uid = userEntity.getUid();
		this.profile = userEntity.getProfile();
		this.name = userEntity.getName();
		this.doctor = false;
	}
}

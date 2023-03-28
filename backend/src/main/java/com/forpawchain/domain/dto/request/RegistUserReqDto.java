package com.forpawchain.domain.dto.request;

import com.forpawchain.domain.Entity.Social;
import com.forpawchain.domain.Entity.UserEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RegistUserReqDto", description = "회원가입 시의 사용자 등록 정보")
public class RegistUserReqDto {
	@ApiModelProperty(name = "id", value = "로그인 ID", required = true, example = "123")
	private String id;

	@ApiModelProperty(name = "social", value = "소셜 로그인 사이트(KAKAO, NAVER, GOOGLE)", required = true, example = "KAKAO")
	private Social social;

	@ApiModelProperty(name = "name", value = "사용자 이름", required = true, example = "최준아")
	private String name;

	@ApiModelProperty(name = "profile", value = "이미지 저장 URL", required = false, example = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Labrador-retriever.jpg/1200px-Labrador-retriever.jpg")
	private String profile;

	/**
	 * RegistUserReqDto를 UserEntity로 변환
	 * @return UserEntity
	 */
	public UserEntity toEntity() {
		return UserEntity.builder()
			.id(id)
			.social(social)
			.name(name)
			.profile(profile)
			.build();
	}
}

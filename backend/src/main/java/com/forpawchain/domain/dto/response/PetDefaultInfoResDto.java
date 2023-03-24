package com.forpawchain.domain.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PetDefaultInfoResDto", description = "반려동물의 목록 출력 시 필요한 정보")
public interface PetDefaultInfoResDto {
	@ApiModelProperty(name = "pid", value = "동물의 인식칩 번호", required = true, example = "41000000000001")
	String getPid();

	@ApiModelProperty(name = "name", value = "동물의 이름", required = true, example = "똥똥이")
	String getName();

	@ApiModelProperty(name = "sex", value = "동물의 성별(MALE, FEMALE)", required = true, example = "MALE")
	String getSex();

	@ApiModelProperty(name = "kind", value = "동물의 종류", required = true, example = "말티즈")
	String getKind();

	@ApiModelProperty(name = "type", value = "동물의 종(DOG, CAT, ETC)", required = true, example = "DOG")
	String getType();

	@ApiModelProperty(name = "spayed", value = "동물의 중성화 여부", required = true, example = "true")
	boolean getSpayed();

	@ApiModelProperty(name = "profile", value = "이미지 저장 URL", required = false, example = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Labrador-retriever.jpg/1200px-Labrador-retriever.jpg")
	String getProfile();
}

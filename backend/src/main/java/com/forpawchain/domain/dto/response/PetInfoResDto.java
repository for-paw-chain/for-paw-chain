package com.forpawchain.domain.dto.response;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PetInfoResDto", description = "반려동물의 기본 정보 및 견적사항")
public interface PetInfoResDto {
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

	@ApiModelProperty(name = "birth", value = "반려동물의 생년월일", required = false, example = "2023-03-23")
	LocalDate getBirth();

	@ApiModelProperty(name = "etc", value = "반려동물에 대한 특이사항", required = false, example = "활발합니다~")
	String getEtc();

	@ApiModelProperty(name = "profile", value = "이미지 저장 URL", required = false, example = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Labrador-retriever.jpg/1200px-Labrador-retriever.jpg")
	String getProfile();

	@ApiModelProperty(name = "region", value = "반려동물이 살고 있는 지역", required = false, example = "서울시 강남구")
	String getRegion();

	@ApiModelProperty(name = "tel", value = "주인의 전화번호", required = false, example = "01012341234")
	String getTel();
}

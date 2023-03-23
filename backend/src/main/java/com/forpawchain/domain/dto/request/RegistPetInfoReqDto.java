package com.forpawchain.domain.dto.request;

import java.awt.event.ItemListener;
import java.time.LocalDate;

import com.forpawchain.domain.Entity.PetInfoEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RegistPetInfoReqDto", description = "반려동물의 견적사항 등록 정보")
public class RegistPetInfoReqDto {
	@ApiModelProperty(name = "pid", value = "반려동물의 인식칩 번호", required = true, example = "41000000000001")
	private String pid;

	@ApiModelProperty(name = "birth", value = "반려동물의 생년월일", required = false, example = "2023-03-23")
	private LocalDate birth;

	@ApiModelProperty(name = "etc", value = "반려동물에 대한 특이사항", required = false, example = "활발합니다~")
	private String etc;

	@ApiModelProperty(name = "region", value = "반려동물이 살고 있는 지역", required = false, example = "서울시 강남구")
	private String region;

	@ApiModelProperty(name = "tel", value = "주인의 전화번호", required = false, example = "01012341234")
	private String tel;

	/**
	 * RegistPetInfoReqDto를 PetInfoEntity로 변환
	 * @return PetInfoEntity
	 */
	public PetInfoEntity toEntity() {
		return PetInfoEntity.builder()
			.pid(pid)
			.birth(birth)
			.etc(etc)
			.profile(profile)
			.region(region)
			.tel(tel)
			.build();
	}
}

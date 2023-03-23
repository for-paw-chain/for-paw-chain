package com.forpawchain.domain.dto.request;

import java.awt.event.ItemListener;
import java.time.LocalDate;

import com.forpawchain.domain.Entity.PetInfoEntity;

import lombok.Data;

@Data
public class RegistPetInfoReqDto {
	private String pid;
	private LocalDate birth;
	private String etc;
	private String profile;
	private String region;
	private String tel;

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

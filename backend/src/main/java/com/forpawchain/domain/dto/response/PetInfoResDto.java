package com.forpawchain.domain.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

public interface PetInfoResDto {
	String getPid();
	String getName();
	String getSex();
	String getKind();
	String getType();
	boolean getSpayed();
	LocalDate getBirth();
	String getEtc();
	String getProfile();
	String getRegion();
	String getTel();
}

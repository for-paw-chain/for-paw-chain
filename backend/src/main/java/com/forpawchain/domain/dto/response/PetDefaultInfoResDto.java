package com.forpawchain.domain.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

public interface PetDefaultInfoResDto {
	String getPid();
	String getName();
	String getSex();
	String getKind();
	String getType();
	boolean getSpayed();
	String getProfile();
}

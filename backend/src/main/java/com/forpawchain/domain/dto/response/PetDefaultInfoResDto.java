package com.forpawchain.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetDefaultInfoResDto {
	private String name;
	private String sex;
	private String type;
	private String kind;
	private boolean spayed;
	private String profile;
}

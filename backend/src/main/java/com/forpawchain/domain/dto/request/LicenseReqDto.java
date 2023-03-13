package com.forpawchain.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseReqDto {

	private String name;
	private String registnum;
	private String tel;
	private int telecom;
}

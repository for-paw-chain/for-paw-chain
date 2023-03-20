package com.forpawchain.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseReqDto {

	@Schema(description = "의사 이름")
	private String name;

	@Schema(description = "의사 주민등록번호")
	private String registnum;

	@Schema(description = "의사 휴대폰 번호")
	private String tel;

	@Schema(description = "의사 휴대폰 통신사")
	private int telecom;
}

package com.forpawchain.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptDetailReqDto {
	@Schema(description = "동물등록번호")
	private String pid;

	@Schema(description = "프로필 사진 1")
	private String profile1;

	@Schema(description = "프로필 사진 2 (선택)")
	private String profile2;

	@Schema(description = "동물 특이사항")
	private String etc;

	@Schema(description = "입양을 원할 시 연락할 수 있는 전화번호")
	private String tel;
}

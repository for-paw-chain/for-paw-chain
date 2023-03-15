package com.forpawchain.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptDetailReqDto {
	private String pid;
	private String profile1;
	private String profile2;
	private String etc;
	private String tel;
}

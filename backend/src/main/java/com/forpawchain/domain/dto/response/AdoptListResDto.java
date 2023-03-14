package com.forpawchain.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptListResDto {

	private String pid;
	private String profile1;
	private String type;
	private String kind;
	private Integer spayed;

	// profile: String
	// type: String
	// kind: String
	// spayed: boolean

}

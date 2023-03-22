package com.forpawchain.domain.dto.response;

import javax.persistence.Entity;

import com.forpawchain.domain.Entity.Type;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public class AdoptListResDto2 {

	private String pid;
	private String profile1;
	private Type type;
	private String kind;
	private boolean spayed;

	public static AdoptListResDto2 toDto() {
		return AdoptListResDto2.builder()

			.build();
	}
}

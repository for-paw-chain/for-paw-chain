package com.forpawchain.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.forpawchain.domain.dto.request.AdoptDetailReqDto;
import com.forpawchain.domain.dto.response.AdoptDetailResDto;
import com.forpawchain.domain.Entity.PetEntity;
import com.forpawchain.repository.AdoptRepository;
import com.forpawchain.repository.PetRegRepository;
import com.forpawchain.repository.PetRepository;

@SpringBootTest
class AdoptServiceTest {

	@Autowired
	AdoptService adoptService;
	@Autowired
	PetRepository petRepository;

	@BeforeEach
	void beforeEach() {

	}

	@AfterEach
	void afterEach() {

	}

	@Test
	@DisplayName("입양 공고 등록하기")
	void registAdopt() {
		Long uid = 1L;
		String pid = "41000000000000";

		// AdoptDetailReqDto adoptDetailReqDto = AdoptDetailReqDto.builder()
		// 	.tel("010-1234-1234")
		// 	.etc("입양해주세요")
		// 	.pid(pid)
		// 	.profile("이미지 url")
		// 	.build();

		// adoptService.registAdopt(adoptDetailReqDto, uid, imageFile);

		// AdoptDetailResDto adoptDetailResDto = adoptService.getAdoptDetail(pid);
		// PetEntity petEntity = petRepository.findByPid(pid);

		// Assertions.assertThat(petEntity.getLost())
	}
}
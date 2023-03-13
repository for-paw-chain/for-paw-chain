package com.forpawchain;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.forpawchain.domain.entity.DoctorLicenseEntity;
import com.forpawchain.repository.DoctorLicenseRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestDataInit {

	private final DoctorLicenseRepository doctorLicenseRepository;

	/**
	 * 초기 데이터 추가
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void initData() {
		// 의사 면허 정보 추가 (실제 서비스에서는 정부 db로 대체됨)
		doctorLicenseRepository.save(new DoctorLicenseEntity("김의사", "1234561234567", "01012341234", 1));
	}
}

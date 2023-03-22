package com.forpawchain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.dto.request.LicenseReqDto;
import com.forpawchain.domain.Entity.DoctorLicenseEntity;

public interface DoctorLicenseRepository extends JpaRepository<DoctorLicenseEntity, Long> {
	Optional<DoctorLicenseEntity> findByNameAndRegistnumAndTelAndTelecom(String name, String registnum, String tel, int telecom);
}

package com.forpawchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.Entity.PetInfoEntity;

public interface PetInfoRepository extends JpaRepository<PetInfoEntity, String> {
	/**
	 * 동물의 견적사항 조회
	 * @param pid
	 * @return PetInfoEntity
	 */
	PetInfoEntity findByPid(String pid);
}

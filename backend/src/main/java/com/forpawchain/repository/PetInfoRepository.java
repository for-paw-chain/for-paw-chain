package com.forpawchain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.Entity.PetInfoEntity;

public interface PetInfoRepository extends JpaRepository<PetInfoEntity, String> {
	/**
	 * 동물의 견적사항 조회
	 * @param pid
	 * @return PetInfoEntity
	 */
	Optional<PetInfoEntity> findByPid(String pid);
}

package com.forpawchain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.Entity.PetEntity;

public interface PetRepository extends JpaRepository<PetEntity, String> {
	/**
	 * 동물의 유기견 여부 및 스마트 컨트랙트 주소 조회
	 * @param pid
	 * @return PetEntity
	 */
	Optional<PetEntity> findByPid(String pid);
}

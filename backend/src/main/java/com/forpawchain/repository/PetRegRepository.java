package com.forpawchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.entity.PetRegEntity;

public interface PetRegRepository extends JpaRepository<PetRegEntity, String> {

	// PetRegEntity findByPid(String pid);
}

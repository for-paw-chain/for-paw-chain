package com.forpawchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.Entity.PetEntity;

public interface PetRepository extends JpaRepository<PetEntity, String> {
	PetEntity findByPid(String pid);
}

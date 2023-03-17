package com.forpawchain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.entity.PetEntity;

public interface PetRepository extends JpaRepository<PetEntity, String> {
	PetEntity findByPid(String pid);
}

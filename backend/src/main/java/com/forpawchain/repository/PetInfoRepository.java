package com.forpawchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.Entity.PetInfoEntity;

public interface PetInfoRepository extends JpaRepository<PetInfoEntity, String> {
}

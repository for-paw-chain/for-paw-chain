package com.forpawchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.entity.PetInfoEntity;

public interface PetInfoRepository extends JpaRepository<PetInfoEntity, String> {
}

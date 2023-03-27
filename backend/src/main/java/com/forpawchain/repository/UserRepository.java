package com.forpawchain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findById(String id);
	Optional<UserEntity> findByIdAndSocial(String id, String social);
	UserEntity findByUid(long uid);
}
package com.forpawchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	/**
	 * 사용자 정보 조회
	 * @param uid
	 * @return UserEntity
	 */
	UserEntity findByUid(long uid);
}

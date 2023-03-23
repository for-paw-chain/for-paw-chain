package com.forpawchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forpawchain.domain.Entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
	/**
	 * 사용자 정보 조회
	 * @param uid
	 * @return UserEntity
	 */
	UserEntity findByUid(long uid);

	// ui의 의사 지갑 주소 반환
	@Query("select wa from UserEntity u where u.uid = :uid")
	Optional<String> findWaByUid(@Param("uid") long uid);
}

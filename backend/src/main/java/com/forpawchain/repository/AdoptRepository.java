package com.forpawchain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.domain.entity.AdoptEntity;

public interface AdoptRepository extends JpaRepository<AdoptEntity, String> {

	@Query(value = "SELECT a.pid, a.profile1, pr.type, pr.kind, pr.spayed\n"
		+ "FROM adopt a, pet_reg pr\n"
		+ "WHERE a.pid = pr.pid\n"
		+ "ORDER BY RAND() \n"
		+ "LIMIT 10", nativeQuery = true)
	List<AdoptListResDto> findTop10ByRand();

	AdoptEntity findByPid(String pid);

	@Query(value = "SELECT a.pid, a.profile1, pr.type, pr.kind, pr.spayed\n"
		+ "FROM adopt a, pet_reg pr\n"
		+ "WHERE a.pid = pr.pid and a.uid = :uid", nativeQuery = true)
	List<AdoptListResDto> findByUid(@Param("uid") Long uid);

	void deleteByPid(String pid);
}

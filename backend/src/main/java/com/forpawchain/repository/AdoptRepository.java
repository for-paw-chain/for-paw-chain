package com.forpawchain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.forpawchain.domain.dto.response.AdoptListResDto;
import com.forpawchain.domain.entity.AdoptEntity;

public interface AdoptRepository extends JpaRepository<AdoptEntity, String> {

	@Query(value = "SELECT * FROM adopt order by RAND() limit 10", nativeQuery = true)
	List<AdoptListResDto> findTop10ByRand();

	AdoptEntity findByPid(String pid);
}

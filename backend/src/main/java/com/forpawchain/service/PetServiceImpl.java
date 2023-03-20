package com.forpawchain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.repository.PetRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {
	private PetRepository petRepository;

	@Override
	public List<PetDefaultInfoResDto> getMyPetList(Long userId) {
		// authentication, petInfo, petReg 테이블 조인해서 찾기
		// 각각 조인해서 조합하기
		return null;
	}
}

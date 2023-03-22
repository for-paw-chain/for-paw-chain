package com.forpawchain.service;

import java.util.List;

import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;

public interface PetService {

	List<PetDefaultInfoResDto> getMyPetList(Long userId);
}

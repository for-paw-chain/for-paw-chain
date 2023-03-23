package com.forpawchain.service;

import java.util.List;

import com.forpawchain.domain.dto.request.RegistPetInfoReqDto;
import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.domain.dto.response.PetInfoResDto;

public interface PetService {

	List<PetDefaultInfoResDto> getMyPetList(Long userId);

	void registPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto);

	void modifyPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto);

	PetInfoResDto getPetInfo(String pid);
}

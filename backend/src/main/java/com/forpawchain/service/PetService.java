package com.forpawchain.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.RegistPetInfoReqDto;
import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.domain.dto.response.PetInfoResDto;

public interface PetService {
	List<PetDefaultInfoResDto> getMyPetList(long uid);

	void registPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto, MultipartFile image) throws IOException;

	void modifyPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto, MultipartFile image) throws IOException;

	PetInfoResDto getPetInfo(String pid);
}
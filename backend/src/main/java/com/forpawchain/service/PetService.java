package com.forpawchain.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.RegistPetInfoReqDto;
import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.domain.dto.response.PetInfoResDto;

public interface PetService {
	/**
	 * 나의 반려동물 목록 조회
	 * @param userId
	 * @return List<PetDefaultInfoResDto>
	 */
	List<PetDefaultInfoResDto> getMyPetList(Long userId);

	/**
	 * 동물의 견적사항 저장
	 * @param userId
	 * @param registPetInfoReqDto
	 * @param image
	 */
	void registPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto, MultipartFile image) throws IOException;

	/**
	 * 동물의 견적사항 수정
	 * @param userId
	 * @param registPetInfoReqDto
	 * @param image
	 */
	void modifyPetInfo(Long userId, RegistPetInfoReqDto registPetInfoReqDto, MultipartFile image) throws IOException;

	/**
	 * 동물의 기본 정보 및 견적사항 조회
	 * @param pid
	 * @return PetInfoResDto
	 */
	PetInfoResDto getPetInfo(String pid);
}

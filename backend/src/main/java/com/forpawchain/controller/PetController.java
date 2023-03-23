package com.forpawchain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forpawchain.domain.dto.request.RegistPetInfoReqDto;
import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.domain.dto.response.PetInfoResDto;
import com.forpawchain.service.PetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {
	private PetService petService;

	// 나의 반려동물 조회
	@GetMapping("/")
	public ResponseEntity<?> getMyPetList(@RequestHeader(value = "accessToken") String accessToken) {
		// Long userId = authController.getUser(accessToken);
		long userId = 1L;

		// TODO: null 처리하기
		List<PetDefaultInfoResDto> myPetList = petService.getMyPetList(userId);
		return ResponseEntity.status(HttpStatus.OK).body(myPetList);
	}

	// 견적사항 등록
	@PostMapping("/info")
	public ResponseEntity<?> registPetInfo(@RequestHeader(value = "accessToken") String accessToken, @RequestBody RegistPetInfoReqDto registPetInfoReqDto) {
		// Long userId = authController.getUser(accessToken);
		long userId = 1L;
		petService.registPetInfo(userId, registPetInfoReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 견적사항 수정
	@PutMapping("/info")
	public ResponseEntity<?> modifyPetInfo(@RequestHeader(value = "accessToken") String accessToken, @RequestBody RegistPetInfoReqDto registPetInfoReqDto) {
		// Long userId = authController.getUser(accessToken);
		long userId = 1L;
		petService.modifyPetInfo(userId, registPetInfoReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 견적사항 조회
	@GetMapping("/info")
	public ResponseEntity<?> getPetInfo(@RequestParam String pid) {
		PetInfoResDto petInfoResDto = petService.getPetInfo(pid);

		// TODO: PetInfo가 없는 경우와 있는 경우, 응답코드 다르게 보내기
		return ResponseEntity.status(HttpStatus.OK).body(petInfoResDto);
	}
}

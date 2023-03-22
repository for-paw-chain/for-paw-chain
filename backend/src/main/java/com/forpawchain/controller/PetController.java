package com.forpawchain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.service.PetService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {
	private AuthController authController;
	private final PetService petService;

	// 나의 반려동물 조회
	@GetMapping("/")
	public ResponseEntity<?> getMyPetList(@RequestHeader(value = "accessToken") String accessToken) {
		Long userId = authController.getUser(accessToken);

		// TODO: null 처리하기
		List<PetDefaultInfoResDto> myPetList = petService.getMyPetList(userId);
		return ResponseEntity.status(HttpStatus.OK).body(myPetList);
	}

	// 견적사항 등록
	// @PostMapping("/info")
	// public ResponseEntity<?> registPetInfo(@RequestBody PetInfo petInfo) {
	//
	// }

	// 견적사항 수정
	// @PutMapping("/info")
	// public ResponseEntity<?> modifyPetInfo(@RequestBody PetInfo petInfo) {
	//
	// }

	// 견적사항 조회
	// @GetMapping("/info")
	// public ResponseEntity<?> getPetInfo(@RequestParam Long pid) {
	//
	// }
}

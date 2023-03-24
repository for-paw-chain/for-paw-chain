package com.forpawchain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.RegistPetInfoReqDto;
import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.domain.dto.response.PetInfoResDto;
import com.forpawchain.service.PetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
@Api(tags = "반려동물 API")
public class PetController {
	private final PetService petService;

	/**
	 * 나의 반려동물 목록 조회
	 * @param accessToken
	 * @return List<PetDefaultInfoResDto>
	 */
	@GetMapping
	@ApiOperation(value = "나의 반려동물 목록 조회", notes = "주인 및 권한 있는 타인에 대한 반려동물 목록을 반환하며, 반려동물에 대한 기본 정보도 반환한다.")
	@ApiImplicitParam(name = "accessToken", value = "accessToken 혹은 refreshToken")
	@ApiResponses({
		@ApiResponse(code = 200, message = "나의 반려동물 목록 반환 성공")
	})
	public ResponseEntity<?> getMyPetList(@RequestHeader(value = "accessToken") String accessToken) {
		// TODO: accessToken으로 userId 찾기
		long userId = 1L;

		List<PetDefaultInfoResDto> myPetList = petService.getMyPetList(userId);
		return ResponseEntity.status(HttpStatus.OK).body(myPetList);
	}

	/**
	 * 견적사항 등록
	 * @param accessToken
	 * @param registPetInfoReqDto
	 * @param image
	 * @return
	 */
	@PostMapping("/info")
	@ApiOperation(value = "견적사항 등록", notes = "주인이 입력한 반려동물에 대한 정보를 저장한다.")
	// @ApiImplicitParams({
	// 	@ApiImplicitParam(name = "accessToken", value = "accessToken 혹은 refreshToken"),
	// 	@ApiImplicitParam(name = "registPetInfoReqDto", value = "반려동물의 정보: birth(생년월일), etc(특이사항), pid(반려동물의 인식칩 번호), region(지역), tel(전화번호)"),
	// 	@ApiImplicitParam(name = "image", value = "사진")
	// })
	@ApiResponses({
		@ApiResponse(code = 201, message = "견적사항 등록 성공")
	})
	public ResponseEntity<?> registPetInfo(@RequestHeader(value = "accessToken") String accessToken, RegistPetInfoReqDto registPetInfoReqDto, @RequestPart(required = false)
		MultipartFile image) {
		// TODO: accessToken으로 userId 찾기
		long userId = 1L;

		// TODO: 모든 내용을 null 혹은 공백을 이용하여 등록하는 경우 처리하기
		// TODO: 이미지 저장하기
		petService.registPetInfo(userId, registPetInfoReqDto, image);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 견적사항 수정
	 * @param accessToken
	 * @param registPetInfoReqDto
	 * @return
	 */
	@PutMapping("/info")
	@ApiOperation(value = "견적사항 수정", notes = "주인이 입력한 반려동물에 대한 정보로 수정한다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "accessToken", value = "accessToken 혹은 refreshToken"),
		@ApiImplicitParam(name = "registPetInfoReqDto", value = "반려동물의 정보: birth(생년월일), etc(특이사항), pid(반려동물의 인식칩 번호), profile(이미지 저장 URL), region(지역), tel(전화번호)")
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "견적사항 수정 성공")
	})
	public ResponseEntity<?> modifyPetInfo(@RequestHeader(value = "accessToken") String accessToken, @RequestBody RegistPetInfoReqDto registPetInfoReqDto) {
		// TODO: accessToken으로 userId 찾기
		long userId = 1L;

		// TODO: null 혹은 공백을 이용하여 등록하는 경우 처리하기
		// TODO: 이미 등록되어 있는 정보와 같은 경우 처리하기
		// TODO: 이미지 저장하기
		petService.modifyPetInfo(userId, registPetInfoReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 견적사항 조회
	 * @param pid
	 * @return PetInfoResDto
	 */
	@GetMapping("/info")
	@ApiOperation(value = "견적사항 조회", notes = "한 마리의 동물에 대한 상세정보(기본정보 및 견적사항)를 반환한다.")
	@ApiImplicitParam(name = "pid", value = "동물의 인식칩 번호")
	@ApiResponses({
		@ApiResponse(code = 200, message = "견적사항 반환 성공")
	})
	public ResponseEntity<?> getPetInfo(@RequestParam String pid) {
		PetInfoResDto petInfoResDto = petService.getPetInfo(pid);

		// TODO: PetInfo가 없는 경우와 있는 경우, 응답코드 다르게 보내기
		return ResponseEntity.status(HttpStatus.OK).body(petInfoResDto);
	}
}

package com.forpawchain.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.forpawchain.domain.dto.request.RegistPetInfoReqDto;
import com.forpawchain.domain.dto.response.PetDefaultInfoResDto;
import com.forpawchain.domain.dto.response.PetInfoResDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.service.PetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
@Api(tags = "반려동물 API")
public class PetController {
	private final UserController userController;
	private final PetService petService;

	@GetMapping
	@ApiOperation(value = "나의 반려동물 목록 조회", notes = "주인 및 권한 있는 타인에 대한 반려동물 목록을 반환하며, 반려동물에 대한 기본 정보도 반환한다.")
	public ResponseEntity<?> getMyPetList() {
		try {
			UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
			List<PetDefaultInfoResDto> myPetList = petService.getMyPetList(userInfoResDto.getUid());

			HashMap<String, List> map = new HashMap<>();
			map.put("content", myPetList);

			return ResponseEntity.status(HttpStatus.OK).body(map);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

	@PostMapping("/info")
	@ApiOperation(value = "견적사항 등록", notes = "주인이 입력한 반려동물에 대한 정보를 저장한다.")
	public ResponseEntity<?> registPetInfo(@RequestPart(name = "content") RegistPetInfoReqDto registPetInfoReqDto,
		@RequestPart(name = "profile", required = false) MultipartFile image) {

		try {
			UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
			petService.registPetInfo(userInfoResDto.getUid(), registPetInfoReqDto, image);

			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

	@PutMapping("/info")
	@ApiOperation(value = "견적사항 수정", notes = "주인이 입력한 반려동물에 대한 정보로 수정한다.")
	public ResponseEntity<?> modifyPetInfo(@RequestPart(name = "content") RegistPetInfoReqDto registPetInfoReqDto,
		@RequestPart(name = "profile", required = false) MultipartFile image) {

		try {
			UserInfoResDto userInfoResDto = userController.getCurrentUserInfo();
			petService.modifyPetInfo(userInfoResDto.getUid(), registPetInfoReqDto, image);

			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

	@GetMapping("/info")
	@ApiOperation(value = "견적사항 조회", notes = "한 마리의 동물에 대한 상세정보(기본정보 및 견적사항)를 반환한다.")
	@ApiImplicitParam(name = "pid", value = "동물의 인식칩 번호")
	@ApiResponses({@ApiResponse(code = 200, message = "견적사항 및 기본 정보 반환 성공"), @ApiResponse(code = 206, message = "기본 정보만 반환 성공")})
	public ResponseEntity<?> getPetInfo(@RequestParam String pid) {
		PetInfoResDto petInfoResDto = petService.getPetInfo(pid);

		// 견적사항이 없는 경우 응답코드 206 반환
		if (petInfoResDto.getProfile() == null && petInfoResDto.getRegion() == null
		&& petInfoResDto.getTel() == null && petInfoResDto.getBirth() == null) {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(petInfoResDto);
		}

		// 견적사항이 있는 경우 응답코드 200 반환
		else {
			return ResponseEntity.status(HttpStatus.OK).body(petInfoResDto);
		}
	}
}

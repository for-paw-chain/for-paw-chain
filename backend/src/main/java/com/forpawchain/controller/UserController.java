package com.forpawchain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "사용자 API")
public class UserController {
	private final UserService userService;

	/**z
	 * 회원가입
	 * @param registUserReqDto
	 * @return
	 */
	@PostMapping
	@ApiOperation(value = "회원가입", notes = "신규 가입한 회원을 저장한다.")
	@ApiImplicitParam(name = "registUserReqDto", value = "사용자의 정보: id(아이디), social(소셜 로그인 사이트 - 카카오, 네이버, 구글), name(이름), profile(이미지 저장 URL)")
	@ApiResponses({
		@ApiResponse(code = 201, message = "회원가입 성공")
	})
	public ResponseEntity<?> registUser(@RequestBody RegistUserReqDto registUserReqDto) {
		userService.registUser(registUserReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// // 로그아웃
	// @GetMapping("/logout")
	// public ResponseEntity<?> logout(@RequestHeader(value = "accessToken") String accessToken) {
	//
	// }

	/**
	 * 회원 정보 조회 (회원 프로필)
	 * @param accessToken
	 * @return UserInfoResDto
	 */
	@GetMapping
	@ApiOperation(value = "회원 정보 조회 (회원 프로필)", notes = "회원의 정보를 조회한다.")
	@ApiImplicitParam(name = "accessToken", value = "accessToken 혹은 refreshToken")
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원 정보 조회 성공")
	})
	public ResponseEntity<?> getUserInfo(@RequestHeader(value = "accessToken") String accessToken) {
		// TODO: accessToken으로 userId 찾기
		long userId = 1L;

		UserInfoResDto userInfo = userService.getUserInfo(userId);
		return ResponseEntity.status(HttpStatus.OK).body(userInfo);
	}

	/**
	 * 회원 탈퇴
	 * @param accessToken
	 * @return
	 */
	@DeleteMapping
	@ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴 여부를 수정한다.")
	@ApiImplicitParam(name = "accessToken", value = "accessToken 혹은 refreshToken")
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원 탈퇴 성공")
	})
	public ResponseEntity<?> removeUser(@RequestHeader(value = "accessToken") String accessToken) {
		// TODO: accessToken으로 userId 찾기
		long userId = 7L;

		// TODO: 로그아웃
		userService.removeUser(userId);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}

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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// 회원가입
	@PostMapping("/")
	public ResponseEntity<?> registUser(@RequestBody RegistUserReqDto registUserReqDto) {
		userService.registUser(registUserReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// // 로그아웃
	// @GetMapping("/logout")
	// public ResponseEntity<?> logout(@RequestHeader(value = "accessToken") String accessToken) {
	//
	// }

	// 회원 정보 조회 (회원 프로필)
	@GetMapping("/")
	public ResponseEntity<?> getUserInfo(@RequestHeader(value = "accessToken") String accessToken) {
		// long userId = authController.getUser(accessToken);
		long userId = 1L;

		UserInfoResDto userInfo = userService.getUserInfo(userId);
		return ResponseEntity.status(HttpStatus.OK).body(userInfo);
	}

	// 회원 탈퇴
	@DeleteMapping("/")
	public ResponseEntity<?> removeUser(@RequestHeader(value = "accessToken") String accessToken) {
		// long userId = authController.getUser(accessToken);
		long userId = 7L;

		userService.removeUser(userId);

		// 로그아웃
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}

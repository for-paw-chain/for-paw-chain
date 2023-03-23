package com.forpawchain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forpawchain.domain.dto.request.LoginUserReqDto;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.service.UserService;
import com.forpawchain.domain.dto.token.TokenInfo;

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

	// 로그인
	@PostMapping("login")
	public ResponseEntity<TokenInfo> login(@RequestBody LoginUserReqDto loginUserReqDto) {
		// 정보 일치하면 token 발급
		String id = loginUserReqDto.getId();
		String social = loginUserReqDto.getSocial();
		TokenInfo tokenInfo = userService.login(id, social);
		return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
	}

	// 로그아웃
	@GetMapping("/logout")
	public void logout(@RequestHeader(value = "Authorization") String accessToken) {
		removeToken(accessToken);
	}

	// 회원 정보 조회 (회원 프로필)
	@GetMapping("/")
	public ResponseEntity<?> getUserInfo(@RequestHeader(value = "Authorization") String accessToken) {
		// long userId = 1L;
		String id = getCurrentUserId();
		UserInfoResDto userInfo = userService.getUserInfo(1L);

		return ResponseEntity.status(HttpStatus.OK).body(userInfo);
	}

	// 회원 탈퇴
	@DeleteMapping("/")
	public ResponseEntity<?> removeUser(@RequestHeader(value = "Authorization") String accessToken) {
		String id = getCurrentUserId();

		userService.removeUser(1L);
		removeToken(accessToken);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// 토큰 정보 조회
	private String getCurrentUserId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication.getName() == null) {
			throw new RuntimeException("권한이 없는 토큰입니다.");
		}
		return authentication.getName();
	}

	// 토큰 정보(refresh) 삭제
	private void removeToken(String accessToken) {

	}

	// 토큰 재발급
}
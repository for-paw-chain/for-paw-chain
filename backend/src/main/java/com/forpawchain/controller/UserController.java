package com.forpawchain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forpawchain.domain.dto.LoginUserDto;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.service.UserService;
import com.forpawchain.domain.dto.TokenInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	// 회원가입
	@PostMapping("/")
	public ResponseEntity<?> registUser(@RequestBody RegistUserReqDto registUserReqDto) {
		registUserReqDto.setSocial(passwordEncoder.encode(registUserReqDto.getSocial()));
		userService.registUser(registUserReqDto);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 로그인
	@PostMapping("login")
	public ResponseEntity<TokenInfo> login(@RequestBody LoginUserDto loginUserReqDto) {
		// 로그인 시마다 정보 일치하면 새로운 token 발급
		TokenInfo tokenInfo = userService.login(loginUserReqDto);
		return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
	}

	// 로그아웃
	@GetMapping("/logout")
	public void logout(@RequestHeader("Authorization") String accessToken) {
		String id = getCurrentUserId();
		userService.logout(resolveToken(accessToken), id);
	}

	// accessToken 재발급
	@PostMapping("/reissue")
	public ResponseEntity<TokenInfo> reissue(@RequestHeader("RefreshToken") String refreshToken) {
		String id = getCurrentUserId();
		return ResponseEntity.ok(userService.reissue(resolveToken(refreshToken), id));
	}

	// 회원 정보 조회 (회원 프로필)
	@GetMapping("/")
	public ResponseEntity<?> getUserInfo() {
		UserInfoResDto userInfo = userService.getUserInfo(getCurrentUserId());

		return ResponseEntity.status(HttpStatus.OK).body(userInfo);
	}

	// 회원 탈퇴
	@DeleteMapping("/")
	public ResponseEntity<?> removeUser() {
		userService.removeUser(getCurrentUserId());

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// 토큰 정보 조회
	private String getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication.getName() == null) {
			throw new BaseException(ErrorMessage.ACCESS_TOKEN_INVALID_SIGNATURE);
		}

		Object principal = authentication.getPrincipal();
		UserDetails userDetails = (UserDetails)principal;

		// 수정 필요
		return userDetails.getUsername();
	}

	// 헤더에서 토큰 추출
	private String resolveToken(String token) {
		return token.substring(7);
	}
}
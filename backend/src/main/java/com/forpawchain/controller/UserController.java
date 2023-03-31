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

import com.forpawchain.domain.dto.token.LoginUserDto;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.service.UserService;
import com.forpawchain.domain.dto.token.TokenInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "유저 API")
public class UserController {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	// 소셜 회원가입 & 로그인
	@PostMapping("/")
	@ApiOperation(value = "SNS 회원가입 & 로그인", notes = "DB 정보가 없다면 자동 회원가입 후 로그인하여 토큰 반환")
	public ResponseEntity<TokenInfo> sns(@RequestBody RegistUserReqDto registUserReqDto) {
		String id = registUserReqDto.getId();
		TokenInfo tokenInfo = null;

		try {
			log.info("유저 가입 유무 검사");
			userService.getUserInfo(id); // 유저 가입 유뮤 검사
		} catch (BaseException e) {
			log.info("회원가입");
			registUser(registUserReqDto);
		} finally {
			log.info("로그인");
			tokenInfo = login(new LoginUserDto(registUserReqDto.getId(), registUserReqDto.getSocial()));
		}

		return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
	}

	// 일반 회원가입 (테스트용)
	@PostMapping("/regist")
	@ApiOperation(value = "일반 회원가입")
	public ResponseEntity<?> commonRegist(@RequestBody RegistUserReqDto registUserReqDto) {
		boolean result = registUser(registUserReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	// 일반 로그인 (테스트용)
	@PostMapping("/login")
	@ApiOperation(value = "일반 로그인")
	public ResponseEntity<?> commonLogin(@RequestBody LoginUserDto loginUserDto) {
		TokenInfo tokenInfo = login(loginUserDto);
		return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
	}

	// 로그아웃
	@GetMapping("/logout")
	@ApiOperation(value = "로그아웃", notes = "refreshToken 삭제")
	public void logout() {
		UserInfoResDto userInfoResDto = getCurrentUserInfo();
		userService.logout(userInfoResDto.getId());
	}

	// accessToken 재발급
	@PostMapping("/reissue")
	@ApiOperation(value = "accessToken 재발급", notes = "accessToken과 refreshToken 재발급")
	public ResponseEntity<TokenInfo> reissue(@RequestHeader("RefreshToken") String refreshToken) {
		UserInfoResDto userInfoResDto = getCurrentUserInfo();

		// TODO: Social 타입 추출
		String social = "KAKAO";

		TokenInfo tokenInfo = userService.reissue(resolveToken(refreshToken), userInfoResDto.getId(), social);
		return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
	}

	// 회원 정보 조회 (회원 프로필)
	@GetMapping("/")
	@ApiOperation("로그인한 회원 정보 조회")
	public ResponseEntity<?> getUserInfo() {
		UserInfoResDto userInfoResDto = getCurrentUserInfo();
		return ResponseEntity.status(HttpStatus.OK).body(userInfoResDto);
	}

	// 회원 탈퇴
	@DeleteMapping("/")
	@ApiOperation(value = "회원 탈퇴", notes = "탈퇴 여부 true 변경, refreshToken 삭제")
	public ResponseEntity<?> removeUser() {
		UserInfoResDto userInfoResDto = getCurrentUserInfo();
		userService.removeUser(userInfoResDto.getUid());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	private boolean registUser(RegistUserReqDto registUserReqDto) {
		registUserReqDto.setSocial(passwordEncoder.encode(registUserReqDto.getSocial()));
		userService.registUser(registUserReqDto);

		return true;
	}

	private TokenInfo login(LoginUserDto loginUserReqDto) {
		// 로그인 시마다 정보 일치하면 새로운 token 발급
		TokenInfo tokenInfo = userService.login(loginUserReqDto);
		return tokenInfo;
	}

	// 토큰 정보 조회
	public UserInfoResDto getCurrentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication.getName() == null) {
			throw new BaseException(ErrorMessage.ACCESS_TOKEN_INVALID_SIGNATURE);
		}

		Object principal = authentication.getPrincipal();
		UserDetails userDetails = (UserDetails)principal;

		String id = userDetails.getUsername();
		// TODO: SOCIAL 값도 가져와서 UNIQUE 예외 처리

		UserInfoResDto userInfoResDto = userService.getUserInfo(id);

		return userInfoResDto;
	}

	// 헤더에서 토큰 추출
	private String resolveToken(String token) {
		return token.substring(7);
	}
}
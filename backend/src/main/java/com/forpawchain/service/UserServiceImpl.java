package com.forpawchain.service;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.forpawchain.auth.JwtTokenProvider;
import com.forpawchain.domain.Entity.RefreshToken;
import com.forpawchain.domain.dto.request.LoginUserReqDto;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.domain.dto.response.TokenResDto;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.RefreshTokenRedisRepository;
import com.forpawchain.repository.UserRepository;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RefreshTokenRedisRepository refreshTokenRedisRepository;
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	private JwtTokenProvider jwtTokenProvider;

	// 사용자를 DB에 등록
	@Override
	@Transactional
	public void registUser(RegistUserReqDto registUserReqDto) {
		userRepository.save(registUserReqDto.toEntity());
	}

	// 사용자 정보 반환
	@Override
	public UserInfoResDto getUserInfo(String id) {
		// TODO: SOCIAL 값도 가져와서 UNIQUE 예외 처리
		UserEntity userEntity = userRepository.findById(id)
			.orElseThrow(() -> new BaseException(ErrorMessage.USER_NOT_FOUND));

		UserInfoResDto userInfo = new UserInfoResDto(userEntity);
		// 의사인 경우
		if (userEntity.getWa() != null) {
			userInfo.setDoctor(true);
		}

		return userInfo;
	}

	// 사용자 탈퇴 여부를 TRUE로 변경
	@Override
	@Transactional
	public void removeUser(long uid) {
		UserEntity userEntity = userRepository.findByUid(uid)
			.orElseThrow(() -> new BaseException(ErrorMessage.USER_NOT_FOUND));

		// user 정보 변경
		UserEntity newUserEntity = UserEntity.builder()
			.uid(userEntity.getUid())
			.id(userEntity.getId())
			.social(userEntity.getSocial())
			.name(userEntity.getName())
			.profile(userEntity.getProfile())
			.wa(userEntity.getWa())
			.del(true)
			.build();
		userRepository.save(newUserEntity);

		// 토큰 정보 삭제
		RefreshToken refreshToken = refreshTokenRedisRepository.findById(userEntity.getId())
			.orElseThrow(() -> new BaseException(ErrorMessage.REFRESH_TOKEN_NOT_MATCH));
		refreshTokenRedisRepository.delete(refreshToken);
	}

	// 로그인 정보를 비교하고 토큰을 생성
	@Override
	public TokenResDto login(LoginUserReqDto loginUserReqDto) {
		return generateToken(loginUserReqDto.getId(), loginUserReqDto.getSocial());
	}

	// REFRESHTOKEN 정보 삭제
	@Override
	// @CacheEvict(value = "user", key = "#username")
	public void logout(String id) {
		// TODO: logoutAccessToken
		refreshTokenRedisRepository.deleteById(id);
	}

	// 토큰 재발급
	@Override
	public TokenResDto reissue(String refreshToken, String id, String social) {
		RefreshToken redisRefreshToken = refreshTokenRedisRepository.findById(id).orElse(null);

		if(refreshToken.equals(redisRefreshToken.getRefreshToken())) {
			return reissueRefreshToken(id, social);
		}

		throw new BaseException(ErrorMessage.REFRESH_TOKEN_NOT_MATCH);
	}

	// 토큰 재발급
	private TokenResDto reissueRefreshToken(String id, String social) {
		// TODO: 기존 REFRESH EXPIRATION에 따라서 재발급되는 토큰 구분
		return generateToken(id, social);
	}

	// REFRESHTOKEN 저장
	private RefreshToken registRefreshToken(String id, String refreshToken) {
		Long remainingMilliSeconds = 1000L * 60 * 60 * 24 * 7 * 2;

		RefreshToken refreshTokenEntity = new RefreshToken(id, refreshToken, remainingMilliSeconds/1000);

		return refreshTokenRedisRepository.save(refreshTokenEntity);
	}

	// 토큰 생성
	private TokenResDto generateToken(String id, String social) {
		// id와 social 정보를 통해서 Authentication 생성
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(id, social);
		// 사용자 확인
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// jwt 토큰 생성
		TokenResDto tokenResDto = jwtTokenProvider.generateToken(authentication);
		registRefreshToken(id, tokenResDto.getRefreshToken());

		return tokenResDto;
	}
}
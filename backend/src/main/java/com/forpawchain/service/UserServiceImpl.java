package com.forpawchain.service;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.forpawchain.auth.JwtTokenProvider;
import com.forpawchain.domain.Entity.RefreshToken;
import com.forpawchain.domain.dto.LoginUserDto;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.domain.dto.TokenInfo;
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

	@Override
	@Transactional
	public void registUser(RegistUserReqDto registUserReqDto) {
		userRepository.save(registUserReqDto.toEntity());
	}

	@Override
	public UserInfoResDto getUserInfo(String id) {
		// UserEntity userEntity = userRepository.findByIdAndSocial(loginUserDto.getId(), loginUserDto.getSocial())
		// 	.orElseThrow(() -> new BaseException(ErrorMessage.NOT_USER_INFO));
		UserEntity userEntity = userRepository.findById(id)
			.orElseThrow(() -> new BaseException(ErrorMessage.NOT_USER_INFO));
		UserInfoResDto userInfo = new UserInfoResDto(userEntity);

		// 의사인 경우
		if (userEntity.getWa() != null) {
			userInfo.setDoctor(true);
		}

		return userInfo;
	}

	@Override
	@Transactional
	public void removeUser(String id) {
		// UserEntity userEntity = userRepository.findByIdAndSocial(loginUserDto.getId(), loginUserDto.getSocial())
		// 	.orElseThrow(() -> new BaseException(ErrorMessage.NOT_USER_INFO));
		UserEntity userEntity = userRepository.findById(id)
			.orElseThrow(() -> new BaseException(ErrorMessage.NOT_USER_INFO));
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
	}

	@Override
	public TokenInfo login(LoginUserDto loginUserDto) {
		// id와 social 정보를 통해서 Authentication 생성
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginUserDto.getId(), loginUserDto.getSocial());
		// 사용자 확인
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// jwt 토큰 생성
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
		registRefreshToken(loginUserDto.getId(), tokenInfo.getRefreshToken());
		return tokenInfo;
	}

	private RefreshToken registRefreshToken(String id, String refreshToken) {
		Long remainingMilliSeconds = 1000L * 60 * 60 * 24 * 7 * 2;
		RefreshToken refreshTokenEntity = RefreshToken.RefreshTokenBuilder;
		return refreshTokenRedisRepository.save(refreshTokenEntity);
	}

	@Override
	public void logout(String accessToken, String refreshToken, String id) {

	}

	@Override
	public TokenInfo reissue(String refreshToken) {
		return null;
	}
}

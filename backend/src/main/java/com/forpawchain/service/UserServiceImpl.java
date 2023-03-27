package com.forpawchain.service;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.forpawchain.auth.JwtTokenProvider;
import com.forpawchain.domain.dto.LoginUserDto;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.domain.dto.token.TokenInfo;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
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
	public TokenInfo login(String id, String social) {
		// id와 social 정보를 통해서 Authentication 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, social);
		// 사용자 확인
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// jwt 토큰 생성
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
		return tokenInfo;
	}
}

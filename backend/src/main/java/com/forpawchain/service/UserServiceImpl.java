package com.forpawchain.service;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.forpawchain.auth.JwtTokenProvider;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.domain.dto.token.TokenInfo;
import com.forpawchain.repository.UserRepository;

import lombok.AllArgsConstructor;

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
	public UserInfoResDto getUserInfo(Long userId) {
		UserEntity userEntity = userRepository.findByUid(userId);
		UserInfoResDto userInfo;

		// 의사인 경우
		if (userEntity.getWa() != null) {
			userInfo = new UserInfoResDto(userEntity.getUid(), userEntity.getProfile(),
				userEntity.getName(), true);
		} else {
			userInfo = new UserInfoResDto(userEntity.getUid(), userEntity.getProfile(),
				userEntity.getName(), false);
		}
		return userInfo;
	}

	@Override
	@Transactional
	public void removeUser(Long userId) {
		UserEntity userEntity = userRepository.findByUid(userId);
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

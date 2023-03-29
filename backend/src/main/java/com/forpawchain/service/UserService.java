package com.forpawchain.service;

import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;

public interface UserService {

	/**
	 * 회원가입
	 * @param registUserReqDto
	 */
	void registUser(RegistUserReqDto registUserReqDto);

	/**
	 * 회원 정보 조회
	 * @param userId
	 * @return UserInfoResDto
	 */
	UserInfoResDto getUserInfo(Long userId);

	/**
	 * 회원 탈퇴
	 * @param userId
	 */
	void removeUser(Long userId);
}

package com.forpawchain.service;

import com.forpawchain.domain.dto.request.LoginUserReqDto;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.domain.dto.response.TokenResDto;

public interface UserService {
	void registUser(RegistUserReqDto registUserReqDto);

	UserInfoResDto getUserInfo(String id);

	void removeUser(long uid);

	TokenResDto login(LoginUserReqDto loginUserReqDto);

	void logout(String id);

	void reRegistUser(LoginUserReqDto loginUserReqDto);

	TokenResDto reissue(String refreshToken, String id, String social);
}

package com.forpawchain.service;

import com.forpawchain.domain.dto.token.LoginUserDto;
import com.forpawchain.domain.dto.request.RegistUserReqDto;
import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.domain.dto.token.TokenInfo;

public interface UserService {

	void registUser(RegistUserReqDto registUserReqDto);

	UserInfoResDto getUserInfo(String id);

	void removeUser(String id);

	TokenInfo login(LoginUserDto loginUserReqDto);

	void logout(String accessToken, String id);

	TokenInfo reissue(String refreshToken, String id);
}

package com.forpawchain.service;

import com.forpawchain.domain.Entity.AuthenticationType;
import com.forpawchain.domain.dto.response.UserResDto;

import java.util.List;

public interface AuthenticationService {
    void giveFriendAuthentication(long uid, long target, String pid);

    void removeAuthentication(long uid, String pid);

    List<UserResDto> getAllAuthenicatedUser(long uid, String pid);

    void giveMasterAuthentication(long uid, long target, String pid);

	AuthenticationType getAuthenticationOfPid(Long uid, String pid);

    String getRegDate(Long uid, String pid);
}

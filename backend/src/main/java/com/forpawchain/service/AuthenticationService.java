package com.forpawchain.service;

import com.forpawchain.domain.Entity.AuthenticationType;
import com.forpawchain.domain.dto.response.UserResDto;

import java.util.List;

public interface AuthenticationService {

    /**
     * 타인에게 권한을 주는 경우
     * 타인의 권한 값 변경 -> save
     */
    void giveFriendAuthentication(long uid, long target, String pid);

    /**
     * 권한 삭제 (나의 강아지는 삭제 불가)
     * delete
     */
    void removeAuthentication(long uid, long target, String pid);

    /**
     * 반려동물에게 권한이 있는 사용자 목록 조회
     * 주체: 반려동물
     * findAllByPid
     */
    List<UserResDto> getAllAuthenicatedUser(long uid, String pid);

    /**
     *  주인권한양도
     *  1. 의사에 의해
     * 2. 주인의 권한 넘겨주기
     * save 2번
     */
    void giveMasterAuthentication(long uid, long target, String pid);

	AuthenticationType getAuthenticationOfPid(Long uid, String pid);
}

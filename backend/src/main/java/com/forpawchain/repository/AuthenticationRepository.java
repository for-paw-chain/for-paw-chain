package com.forpawchain.repository;

import com.forpawchain.domain.entity.AuthenticationEntity;
import com.forpawchain.domain.entity.AuthenticationId;
import com.forpawchain.domain.entity.AuthenticationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, AuthenticationId> {
    List<AuthenticationEntity> findAllByAid(AuthenticationId aid);
    AuthenticationEntity findByAidUidAndAidPid(long uid, String pid);
    List<AuthenticationEntity> findAllByAidUid(long uid);
    List<AuthenticationEntity> findAllByAidPid(String pid);

    /**
     * 타인에게 권한을 주는 경우
     * 타인의 권한 값 변경 -> save
     */

    /**
     * 권한 삭제 (나의 강아지는 삭제 불가)
     * delete
     */
    void deleteByAidUidAndAidPid(long uid, String pid);

    /**
     * 반려동물에게 권한이 있는 사용자 목록 조회
     * 주체: 반려동물
     * findAllByPid
     */

    /**
     *  주인권한양도
     *  1. 의사에 의해
     */

    /**
     * 2. 주인의 권한 넘겨주기
     * save 2번
     *
     */
}
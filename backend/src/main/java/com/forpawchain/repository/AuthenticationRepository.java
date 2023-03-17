package com.forpawchain.repository;

import com.forpawchain.domain.entity.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, String> {

    /**
     * 타인에게 권한을 주는 경우
     */

    /**
     * 권한 삭제 (나의 강아지는 삭제 불가)
     */

    /**
     * 반려동물에게 권한이 있는 사용자 목록 조회
     * 주체: 반려동물
     */

    /**
     *  권한양도
     *  1. 의사에 의해
     *  2. 주인에 의해 (주인의 권한은 권한 있는 타인이 됨)
     */
}

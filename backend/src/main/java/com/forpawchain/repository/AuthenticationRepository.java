package com.forpawchain.repository;

import com.forpawchain.domain.Entity.AuthenticationEntity;
import com.forpawchain.domain.Entity.AuthenticationId;
import com.forpawchain.domain.Entity.AuthenticationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, AuthenticationId> {
    Optional<AuthenticationEntity> findByAuthId(AuthenticationId id);
    Optional<AuthenticationEntity> findByAuthIdUidAndAuthIdPid(long uid, String pid);
    @Query("select a.type from AuthenticationEntity a \n" +
            "where a.authId.uid = :uid and a.authId.pid = :pid")
    AuthenticationType findTypeByAuthIdUidAndAuthIdPid(@Param("uid") long uid, @Param("pid") String pid);
    List<AuthenticationEntity> findAllByAuthIdUid(long uid);
    List<AuthenticationEntity> findAllByAuthIdPid(String pid);
    void deleteByAuthIdUidAndAuthIdPid(long uid, String pid);
}
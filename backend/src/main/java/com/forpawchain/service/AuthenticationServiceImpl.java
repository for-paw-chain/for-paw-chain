package com.forpawchain.service;

import com.forpawchain.domain.dto.response.UserInfoResDto;
import com.forpawchain.domain.dto.response.UserResDto;
import com.forpawchain.domain.Entity.*;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.AuthenticationRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    /**
     *
     * @param target: 권한을 받는 사람의 uid
     * @param pid: 권한과 관련된 동물의 pid
     */
    @Override
    public void giveFriendAuthentication(long uid, long target, String pid) throws BaseException {
        try {
            Optional<String> targetWa = userRepository.findWaByUid(target);
            // 받는 사람이 의사라면
            if (!targetWa.isEmpty()) {
                return;
            }

            AuthenticationType authType = authenticationRepository.findAuthenticationTypeByUidAndPid(uid, pid);

            Optional<AuthenticationEntity> orgEntity = authenticationRepository.findByAuthIdUidAndAuthIdPid(target, pid);

            AuthenticationId authID = new AuthenticationId(target, pid);

            UserEntity userEntity = userRepository.findByUid(target)
                .orElseThrow(() -> new BaseException(ErrorMessage.PET_NOT_FOUND));

            PetEntity petEntity = petRepository.findByPid(pid)
                .orElseThrow(() -> new BaseException(ErrorMessage.PET_NOT_FOUND));

            AuthenticationEntity newEntity = AuthenticationEntity
                    .builder()
                    .authId(authID)
                    .type(AuthenticationType.FRIEND) // FRIEND 권한으로 변경된다.
                    .regTime(LocalDate.now())
                    .user(userEntity)
                    .pet(petEntity)
                    .build();

            // 받 는 사람의 권한을 FRINED로 변경
            authenticationRepository.save(newEntity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorMessage.QUERY_FAIL_EXCEPTION);
        }
     }

    /**
     *
     * @param uid: 지워질 권한을 갖고 있는 사람의 uid
     * @param pid: 지워질 권한과 관련된 동물의 pid
     */
    @Override
    public void removeAuthentication(long uid, String pid) {
        try {
            // uid, pid인 권한 삭제
            authenticationRepository.deleteByAuthIdUidAndAuthIdPid(uid, pid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorMessage.QUERY_FAIL_EXCEPTION);
        }
    }

    /**
     *
     * @param pid: pid와 관련된 모든 권한 조회
     * @return pid에 대한 권한 모두 반환
     */
    @Override
    public List<UserResDto> getAllAuthenicatedUser(long uid, String pid) {
        try {
            Optional<String> targetWa = userRepository.findWaByUid(uid);
            // 받는 사람이 의사라면
            if (!targetWa.isEmpty()) {
                throw new BaseException(ErrorMessage.AUTH_NOT_NEEDED);
            }

            return authenticationRepository.findUserAllByPid(pid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorMessage.QUERY_FAIL_EXCEPTION);
        }
    }
    /**
     *  주인권한양도
     *  1. 의사에 의해
     * 2. 주인의 권한 넘겨주기
     * save 2번
     */
    /**
     *
     * @param uid: 권한을 주는 사람의 uid
     * @param target: 권한을 받는 사람의 uid
     * @param pid: 권한과 관련된 동물 pid
     *
     * 1. 의사가 주인에게 권한을 주는 경우 -> 주인은 1명이어야 한다.
     * 2. 주인이 주인 권한을 다른이에게 넘겨주는 경우
     */
    @Override
    public void giveMasterAuthentication(long uid, long target, String pid) {
        try {
            Optional<String> targetWa = userRepository.findWaByUid(target);
            // 받는 사람이 의사라면
            if (!targetWa.isEmpty()) {
                return;
            }

            AuthenticationType senderAuthentication = authenticationRepository.findAuthenticationTypeByUidAndPid(uid, pid);
            Optional<String> uidWa = userRepository.findWaByUid(uid);
            PetEntity petEntity = petRepository.findByPid(pid)
                .orElseThrow(() -> new BaseException(ErrorMessage.PET_NOT_FOUND));

            // 의사일 경우
            if (!uidWa.isEmpty()) {
                // 원래의 주인 uid 찾기
                Optional<Long> sender = authenticationRepository.findUidByPidAndType(pid, AuthenticationType.MASTER);

                // 유기견인 경우
                if (sender.isEmpty()) {
                    UserEntity targetEntity = userRepository.findByUid(target)
                        .orElseThrow(() -> new BaseException(ErrorMessage.USER_NOT_FOUND));

                    AuthenticationEntity authenticationEntity = AuthenticationEntity
                            .builder()
                            .authId(new AuthenticationId(target, pid))
                            .user(targetEntity)
                            .pet(petEntity)
                            .regTime(LocalDate.now())
                            .type(AuthenticationType.MASTER)
                            .build();

                    authenticationRepository.save(authenticationEntity);
                }
                else {
                    Optional<Long> masterid = authenticationRepository.findUidByPidAndType(pid, AuthenticationType.MASTER);
                    if (!masterid.isEmpty())
                        moveAuthentication(masterid.get(), target, pid, AuthenticationType.MASTER);
                }
            }
            // 주인이 주인 권한을 넘겨주는 경우
            else {
                moveAuthentication(uid, target, pid, AuthenticationType.MASTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorMessage.QUERY_FAIL_EXCEPTION);
        }
    }

    @Override
    public AuthenticationType getAuthenticationOfPid(Long uid, String pid) {
        String authentication = authenticationRepository.findTypeByAuthIdUidAndAuthIdPid(uid, pid);

        if (authentication == null) {
            throw new BaseException(ErrorMessage.NOT_EXIST_CONTENT);
        }

        AuthenticationType authenticationType = AuthenticationType.valueOf(authentication);

        return authenticationType;
    }

    @Override
    public String getRegDate(Long uid, String pid) {
        LocalDate regDate = authenticationRepository.findRegDateByAuthIdUidAndAuthIdPid(uid, pid);

        if (regDate == null) {
            throw new BaseException(ErrorMessage.NOT_EXIST_CONTENT);
        }

        return regDate.toString();
    }

    // 주인의 권한이 제거되는 경우
    private void moveAuthentication(long frm, long to, String pid, AuthenticationType type) {
        try {
            // 권한을 주는 사람의 권한
            Optional<AuthenticationEntity> frmAuthentication = authenticationRepository.findByAuthId(new AuthenticationId(frm, pid));

            if (frmAuthentication.isEmpty()) {
                throw new BaseException(ErrorMessage.AUTH_NOT_FOUND);
            }

            authenticationRepository.deleteByAuthIdUidAndAuthIdPid(frm, pid);

            AuthenticationEntity toAuthentication = AuthenticationEntity
                    .builder()
                    .authId(new AuthenticationId(to, pid))
                    .user(userRepository.findByUid(to).orElse(null))
                    .pet(frmAuthentication.get().getPet())
                    .regTime(LocalDate.now())
                    .type(type)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ErrorMessage.QUERY_FAIL_EXCEPTION);
        }
    }
}

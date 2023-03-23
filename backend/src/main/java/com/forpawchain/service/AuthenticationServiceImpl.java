package com.forpawchain.service;

import com.forpawchain.domain.dto.response.UserResDto;
import com.forpawchain.domain.Entity.*;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.AuthenticationRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
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
     * @param taraget: 권한을 받는 사람의 uid
     * @param pid: 권한과 관련된 동물의 pid
     */
    @Override
    public void giveFriendAuthentication(long uid, long taraget, String pid) throws BaseException {
        Optional<AuthenticationEntity> orgEntity = authenticationRepository.findByAuthIdUidAndAuthIdPid(taraget, pid);

        AuthenticationId authID = new AuthenticationId(taraget, pid);

        UserEntity userEntity = userRepository.findByUid(taraget);
        PetEntity petEntity = petRepository.findByPid(pid);

        // 유저 정보가 존재하지 않는 경우
        // pet
        if (userEntity == null || userEntity.isDel()) {
            throw new BaseException(ErrorMessage.USER_NOT_FOUND);
        }

        // pet 정보가 존재하지 않는 경우
        // pet의 주인이 서비스를 탈퇴한 경우
        if (petEntity == null) {
            throw new BaseException(ErrorMessage.PET_NOT_FOUND);
        }

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
     }

    /**
     *
     * @param uid: 지워질 권한을 갖고 있는 사람의 uid
     * @param pid: 지워질 권한과 관련된 동물의 pid
     */
    @Override
    public void removeAuthentication(long uid, long target, String pid) {
        // uid, pid인 권한 삭제   
        authenticationRepository.deleteByAuthIdUidAndAuthIdPid(uid, pid);
    }

    /**
     * 
     * @param pid: pid와 관련된 모든 권한 조회
     * @return pid에 대한 권한 모두 반환
     */
    @Override
    public List<UserResDto> getAllAuthenicatedUser(long uid, String pid) {
        List<UserResDto> userList = new ArrayList<>();
        // pid에 대한 권한을 갖고 있는 모든 사람
        for (AuthenticationEntity authenticationEntity : authenticationRepository.findAllByAuthIdPid(pid)) {
            UserEntity userEntity = authenticationEntity.getUser();
            UserResDto userResDto = UserResDto
                    .builder().name(userEntity.getName())
                    .pfofile(userEntity.getProfile())
                    .build();
            userList.add(userResDto);
        }
        return userList;
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
     */
    @Override
    public void giveMasterAuthentication(long uid, long target, String pid) {

        List<AuthenticationEntity> aidList = authenticationRepository.findAllByAuthIdUid(target);
        List<AuthenticationEntity> pidList = authenticationRepository.findAllByAuthIdPid(pid);
        // 현재 주인 정보
        UserEntity fromEntity = userRepository.findByUid(uid);
        
        // pid에 대한 권한 받을 사람의 권한 조회
        Optional<AuthenticationEntity> authentication = authenticationRepository.findById(new AuthenticationId(target, pid));
        // 권한을 받을 사람 정보
        UserEntity userEntity = userRepository.findByUid(target);
        PetEntity petEntity = petRepository.findByPid(pid);

        // 권할 받을 사람의 권한을 master로 변경
        AuthenticationEntity newEntity = AuthenticationEntity
                .builder()
                .authId(new AuthenticationId(target, pid))
                .type(AuthenticationType.MASTER) // MASTER로 설정
                .regTime(LocalDate.now())
                .user(userEntity)
                .pet(petEntity)
                .build();
        authenticationRepository.save(newEntity);

        // 의사가 아니면 권한을 주는 사람의 권한 제거
        if ("".equals(fromEntity.getWa())) {
            removeAuthentication(uid, target, pid);
        }
    }
}

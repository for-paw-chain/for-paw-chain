package com.forpawchain.service;

import com.forpawchain.domain.dto.response.UserResDto;
import com.forpawchain.domain.Entity.*;
import com.forpawchain.repository.AuthenticationRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    /**
     *
     * @param to: 권한을 받는 사람의 uid
     * @param pid: 권한과 관련된 동물의 pid
     */
    @Override
    public void giveFriendAuthentication(long to, String pid) {

        UserEntity userEntity = userRepository.findByUid(to);
        PetEntity petEntity = petRepository.findByPid(pid);

        // 받는 사람의 현재 권한
        // 받는 사람의 권한을 FRINED로 변경
        AuthenticationEntity newEntity = AuthenticationEntity
                .builder()
                .aid(new AuthenticationId(to, pid))
                .type(AuthenticationType.FRIEND) // FRIEND 권한으로 변경된다.
                .regTime(LocalDate.now())
                .user(userEntity)
                .pet(petEntity)
                .build();

        authenticationRepository.save(newEntity);
    }

    /**
     *
     * @param uid: 지워질 권한을 갖고 있는 사람의 uid
     * @param pid: 지워질 권한과 관련된 동물의 pid
     */
    @Override
    public void removeAuthentication(long uid, String pid) {
        // uid, pid인 권한 삭제   
        authenticationRepository.deleteByAidUidAndAidPid(uid, pid);
    }

    /**
     * 
     * @param pid: pid와 관련된 모든 권한 조회
     * @return pid에 대한 권한 모두 반환
     */
    @Override
    public List<UserResDto> getAllAuthenicatedUser(String pid) {
        List<UserResDto> userList = new ArrayList<>();
        // pid에 대한 권한을 갖고 있는 모든 사람
        for (AuthenticationEntity authenticationEntity : authenticationRepository.findAllByAidPid(pid)) {
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
     * @param frm: 권한을 주는 사람의 uid
     * @param to: 권한을 받는 사람의 uid
     * @param pid: 권한과 관련된 동물 pid
     */
    @Override
    public void giveMasterAuthentication(long frm, long to, String pid) {
        // 현재 주인 정보
        UserEntity fromEntity = userRepository.findByUid(frm);
        
        // pid에 대한 권한 받을 사람의 권한 조회
        AuthenticationEntity authentication = authenticationRepository.findByAidUidAndAidPid(to, pid);
        // 권한을 받을 사람 정보
        UserEntity userEntity = userRepository.findByUid(to);
        PetEntity petEntity = petRepository.findByPid(pid);
        // 기존에 어떠한 권한이라도 있었다면 갱신되지 않음
        // 권한이 없다가 권한이 생긴 경우에는 현재 시간으로 저장된다
        LocalDate newDate = authentication == null ? LocalDate.now() : authentication.getRegTime();

        // 권할 받을 사람의 권한을 master로 변경
        AuthenticationEntity newEntity = AuthenticationEntity
                .builder()
                .aid(new AuthenticationId(to, pid))
                .type(AuthenticationType.MASTER) // MASTER로 설정
                .regTime(newDate)
                .user(userEntity)
                .pet(petEntity)
                .build();
        authenticationRepository.save(newEntity);

        // 의사가 아니면 권한을 주는 사람의 권한 제거
        if (fromEntity.getWa().isEmpty()) {
            removeAuthentication(frm, pid);
        }
    }
}

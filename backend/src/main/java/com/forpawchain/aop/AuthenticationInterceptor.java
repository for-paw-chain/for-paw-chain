package com.forpawchain.aop;

import com.forpawchain.domain.Entity.PetEntity;
import com.forpawchain.domain.Entity.PetInfoEntity;
import com.forpawchain.domain.Entity.UserEntity;
import com.forpawchain.exception.BaseException;
import com.forpawchain.exception.ErrorMessage;
import com.forpawchain.repository.PetInfoRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetInfoRepository petInfoRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BaseException {
        String token = request.getHeader("access-token");
        long uid = 1;
        String pid = request.getParameter("pid");

        UserEntity userEntity = userRepository.findByUid(uid);
        PetEntity petEntity = petRepository.findByPid(pid);
        
        // 아직 가입하지 않은 유저이거나 탈퇴한 유저
        if (userEntity == null || userEntity.isDel()) {
            throw new BaseException(ErrorMessage.USER_NOT_FOUND);
        }

        // 아직 가입하지 않은 유저이거나 탈퇴한 유저
        if (petEntity == null) {
            throw new BaseException(ErrorMessage.PET_NOT_FOUND);
        }

        PetInfoEntity petInfoEntity = petInfoRepository.findByPid(pid);
        if (petInfoEntity == null) {
            throw new BaseException(ErrorMessage.PET_NOT_FOUND);
        }

        long receiver = Long.parseLong(request.getParameter("receiver"));

        if (receiver != 0) {

            UserEntity receiverEntity = userRepository.findByUid(receiver);

            if (userEntity == null || userEntity.isDel()) {
                throw new BaseException(ErrorMessage.USER_NOT_FOUND);
            }
        }

        return true;
    }
}

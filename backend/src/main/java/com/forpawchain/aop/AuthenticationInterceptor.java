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
        String pid = request.getParameter("pid");

        PetEntity petEntity = petRepository.findByPid(pid)
            .orElseThrow(() -> new BaseException(ErrorMessage.PET_NOT_FOUND));

        PetInfoEntity petInfoEntity = petInfoRepository.findByPid(pid)
            .orElseThrow(() -> new BaseException(ErrorMessage.PET_NOT_FOUND));

        String receiver = request.getParameter("receiver");
        if (receiver != null) {
            UserEntity receiverEntity = userRepository.findByUid(Long.parseLong(receiver)).orElseThrow(null);

            if (receiverEntity == null || receiverEntity.isDel()) {
                throw new BaseException(ErrorMessage.USER_NOT_FOUND);
            }
        }

        return true;
    }
}

package com.forpawchain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* com.forpawchain...*(..))")
    public void somethingBefore(ProceedingJoinPoint pjp) throws Throwable{
        log.info(">>> ");
    }
}

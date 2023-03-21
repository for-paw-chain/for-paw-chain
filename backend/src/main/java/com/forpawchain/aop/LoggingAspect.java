package com.forpawchain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before(value="execution(* com.forpawchain.controller.*.*(..))")
    public void loggingBefore(JoinPoint joinPoint) throws Throwable{
        log.info(">>>>>> Called  : {}", joinPoint.getSignature().getName());
        log.info(">>>>>> Param : {}", Arrays.toString(joinPoint.getArgs()));
    }
    @After(value="execution(* com.forpawchain..*.*.*(..))")
    public void loggingAfter(JoinPoint joinPoint) throws Throwable{
        log.info(">>>>>> End  : {}", joinPoint.getSignature().getName());
    }
}

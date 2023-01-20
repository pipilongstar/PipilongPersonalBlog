package com.pipilong.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/1/20
 * @description
 */
@Component
@Slf4j
@Aspect
public class LogAspectJ {

    @Pointcut("@annotation(com.pipilong.annotation.ErrorLog)")
    void print(){}

    @Around("print()")
    public Object errorLog(ProceedingJoinPoint joinPoint) throws Throwable{

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;

    }


}








































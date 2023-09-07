package com.pipilong.aop;

import com.pipilong.annotation.ErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

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

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ErrorLog errorLog = method.getAnnotation(ErrorLog.class);
        boolean isThrow = errorLog.value();

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error(method.getName()+":"+e.getMessage());
            if(isThrow) throw e;
        }
        return null;

    }
}








































package com.pipilong.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author pipilong
 * @createTime 2023/2/1
 * @description
 */
@RestControllerAdvice(basePackages = "com.pipilong.controller")
@Slf4j
public class CommonControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception e){

        log.error("控制器：{},异常类型：{}，出现异常：{}",this.getClass().getName(),e.getClass().getName(),e.getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

}













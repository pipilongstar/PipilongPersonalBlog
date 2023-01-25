package com.pipilong.controller;

import com.pipilong.service.SmsService;
import com.pipilong.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private VerificationService verificationService;

    /**
     * 接收手机号，发送验证码
     * @param telephone 手机号
     * @param httpSession 用户session
     */
    @PostMapping("/sendcode/{telephone}")
    public ResponseEntity<String> sendCode(
            @PathVariable String telephone,
            HttpSession httpSession){

        String sessionId = httpSession.getId();
        smsService.verificationCodeProcessing(telephone,sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根据用户id取查找手机号并发送验证码
     * @param userId 用户id
     * @param httpSession 用户session
     * @return nothing
     */
    @PostMapping("/sendcode")
    public ResponseEntity<String> sendCodeByUserId(@RequestParam("userid") String userId, HttpSession httpSession){

        smsService.sendSmsByUserId(userId,httpSession.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
































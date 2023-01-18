package com.pipilong.controller;

import com.pipilong.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;

    private final Pattern pattern=Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 接收手机号，发送验证码
     * @param telephone
     * @param httpSession
     */
    @PostMapping("/sendcode/{telephone}")
    public ResponseEntity<String> sendCode(
            @PathVariable
            String telephone,
            HttpSession httpSession){

        Matcher matcher = pattern.matcher(telephone);
        if(!matcher.matches()) return new ResponseEntity<>("手机号格式错误",HttpStatus.BAD_REQUEST);
        String sessionId = httpSession.getId();
        smsService.verificationCodeProcessing(telephone,sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
































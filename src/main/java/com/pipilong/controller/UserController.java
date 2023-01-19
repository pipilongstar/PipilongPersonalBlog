package com.pipilong.controller;

import com.pipilong.pojo.User;
import com.pipilong.service.SmsService;
import com.pipilong.service.UserService;
import com.sun.deploy.association.RegisterFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * @param user 用户信息
     * @param code 验证码
     * @param session 用户此时会话的session
     * @return true or false
     */
    @PutMapping("/register/{code}")
    public ResponseEntity<String> register(
            @RequestBody User user,
            @PathVariable("code") String code,
            HttpSession session
    ){
        String sessionId = session.getId();
        if(!smsService.verificationCode(code,sessionId)){
            return new ResponseEntity<>("验证码错误", HttpStatus.UNAUTHORIZED);
        }

        String userId = null;
        try {
            userId = userService.register(user, sessionId);
        } catch (RegisterFailedException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userId,HttpStatus.OK);
    }

}
































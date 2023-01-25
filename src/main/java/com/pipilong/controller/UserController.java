package com.pipilong.controller;

import com.pipilong.exception.ModifyException;
import com.pipilong.pojo.User;
import com.pipilong.service.SmsService;
import com.pipilong.service.UserService;
import com.pipilong.service.VerificationService;
import com.sun.deploy.association.RegisterFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private VerificationService verificationService;

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
        if(verificationService.verificationCode(code, sessionId)){
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

    /**
     * 修改个人简介
     * @param user 用户信息
     * @return ture or false
     */
    @PutMapping("/modifyprofile")
    public ResponseEntity<String> modifyProfile(@RequestBody User user){

        try {
            userService.modifyProfile(user);
        } catch (ModifyException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 修改手机号
     * @param telephone 新手机号
     * @param userId 用户id
     * @param code 验证码
     * @param session 用户session
     * @return nothing
     */
    @PostMapping("/modifyphone")
    public ResponseEntity<String> modifyPhone(
            @RequestParam("telephone") String telephone,
            @RequestParam("userid") String userId,
            @RequestParam("code") String code,
            HttpSession session
    ){

        if(verificationService.verificationCode(code, session.getId())) return new ResponseEntity<>("验证码错误",HttpStatus.UNAUTHORIZED);

        try {
            userService.modifyTelephone(telephone,userId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 修改电子邮箱
     * @param email 新电子邮箱
     * @param userid 用户id
     * @param code 验证码
     * @param session 用户session
     * @return nothing
     */
    @PostMapping("/modifyemail")
    public ResponseEntity<String> modifyEmail(
            @RequestParam("email") String email,
            @RequestParam("userid") String userid,
            @RequestParam("code") String code,
            HttpSession session){

        if(verificationService.verificationCode(code, session.getId())) return new ResponseEntity<>("验证码错误",HttpStatus.UNAUTHORIZED);
        try {
            userService.modifyEmail(email,userid);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/modifypassword")
    public ResponseEntity<String> modifyPassword(
            @RequestParam("oldpassword") String oldPassword,
            @RequestParam("newpassword") String newPassword,
            @RequestParam("userid") String userid
    ){

        try {
            userService.modifyPassword(oldPassword,newPassword,userid);
        } catch (ModifyException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
































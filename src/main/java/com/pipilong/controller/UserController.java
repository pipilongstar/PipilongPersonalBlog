package com.pipilong.controller;

import com.pipilong.exception.ModifyException;
import com.pipilong.pojo.User;
import com.pipilong.service.UserService;
import com.pipilong.service.VerificationService;
import com.sun.deploy.association.RegisterFailedException;
import javafx.scene.input.DataFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.web.bind.annotation.*;
import sun.text.resources.FormatData;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

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
    ) throws RegisterFailedException {
        String sessionId = session.getId();
        if(verificationService.verificationCode(code, sessionId)){
            return new ResponseEntity<>("验证码错误", HttpStatus.UNAUTHORIZED);
        }
        String userId = userService.register(user, sessionId);
        return new ResponseEntity<>(userId,HttpStatus.OK);
    }

    /**
     * 修改个人简介
     * @param user 用户信息
     * @return ture or false
     */
    @PutMapping("/modifyprofile")
    public ResponseEntity<String> modifyProfile(@RequestBody User user) throws ModifyException {

        userService.modifyProfile(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 修改手机号
     * @param telephone 新手机号
     * @param userId 用户id
     * @return nothing
     */
    @PutMapping("/modifyphone")
    public ResponseEntity<String> modifyPhone(
            @RequestParam("telephone") String telephone,
            @RequestParam("userid") String userId
    ) throws ModifyException {

        userService.modifyTelephone(telephone,userId);

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
    @PutMapping("/modifyemail")
    public ResponseEntity<String> modifyEmail(
            @RequestParam("email") String email,
            @RequestParam("userid") String userid,
            HttpSession session) throws ModifyException {

        userService.modifyEmail(email,userid);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param userid 用户id
     * @return true or false
     */
    @PostMapping("/modifypassword")
    public ResponseEntity<String> modifyPassword(
            @RequestParam("oldpassword") String oldPassword,
            @RequestParam("newpassword") String newPassword,
            @RequestParam("userid") String userid
    ) throws ModifyException {

        userService.modifyPassword(oldPassword,newPassword,userid);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 获取个人资料
     * @param session 用户session
     * @return true or false
     * @throws LoginException 用户未登录
     */
    @GetMapping("/getprofile")
    public ResponseEntity<User> getProfile(HttpSession session) throws LoginException {

        String sessionId = session.getId();
        User user = userService.getProfile(sessionId);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
































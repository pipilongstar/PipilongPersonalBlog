package com.pipilong.controller;

import com.pipilong.service.SelectExistService;
import com.pipilong.service.VerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pipilong
 * @createTime 2023/1/19
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/verification")
public class VerificationController {

    @Autowired
    private SelectExistService selectExistService;
    @Autowired
    private VerificationService verificationService;
    private final Pattern patternTelephone = Pattern.compile("^1[3-9]\\d{9}$");
    private final Pattern patternEmail = Pattern.compile("^[A-Za-z0-9-._]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$");

    /**
     * 验证手机号格式是否正确，是否存在
     * @param telephone 手机号
     * @return true or false
     */
    @GetMapping("/telephone/{telephone}")
    public ResponseEntity<String> isExistTelephone(@PathVariable("telephone") String telephone){

        Matcher matcher = patternTelephone.matcher(telephone);
        if(!matcher.matches()) return new ResponseEntity<>("手机号格式错误", HttpStatus.BAD_REQUEST);
        if(selectExistService.telephone(telephone)) return new ResponseEntity<>("手机号已经存在",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 验证电子邮件格式是否正确，是否存在
     * @param email 电子邮件
     * @return true or false
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<String> isExistEmail(@PathVariable("email") String email){

        Matcher matcher = patternEmail.matcher(email);
        if(!matcher.matches()) return new ResponseEntity<>("邮箱格式错误", HttpStatus.BAD_REQUEST);
        if(selectExistService.email(email)) return new ResponseEntity<>("电子邮箱已经存在",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 验证验证码接口
     * @param code 验证码
     * @param session 用户session
     * @return nothing
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<String> verificationCode(@PathVariable("code") String code, HttpSession session){
        
        if(verificationService.verificationCode(code, session.getId())) return new ResponseEntity<>("验证码错误",HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 判断密码是否已经设置
     * @param userId 用户id
     * @return true or false
     */
    @GetMapping("/password/{userid}")
    public ResponseEntity<String> isExistPassword(@PathVariable("userid") String userId){

        if(!selectExistService.password(userId)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("notNull",HttpStatus.OK);
    }

    /**
     * 验证是否已经登录
     * @param httpSession 用户session
     * @return true or false
     */
    @GetMapping("/isLogin")
    public ResponseEntity<String> isLogin(HttpSession httpSession){
        String sessionId=httpSession.getId();

        String userId = verificationService.isLogin(sessionId);
        if(userId==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userId,HttpStatus.OK);
    }

}















































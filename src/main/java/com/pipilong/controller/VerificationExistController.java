package com.pipilong.controller;

import com.pipilong.service.SelectExistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pipilong
 * @createTime 2023/1/19
 * @description
 */
@RestController
@RequestMapping("/verification")
public class VerificationExistController {

    @Autowired
    private SelectExistService selectExistService;
    private final Pattern patternTelephone = Pattern.compile("^1[3-9]\\d{9}$");

    private final Pattern patternEmail = Pattern.compile("^[A-Za-z0-9-._]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$");

    /**
     * 验证手机号格式是否正确，是否存在
     * @param telephone 手机号
     * @return true or false
     */
    @GetMapping("/telephone/{telephone}")
    public ResponseEntity<String> telephone(@PathVariable("telephone") String telephone){

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
    public ResponseEntity<String> email(@PathVariable("email") String email){

        Matcher matcher = patternEmail.matcher(email);
        if(!matcher.matches()) return new ResponseEntity<>("邮箱格式错误", HttpStatus.BAD_REQUEST);
        if(selectExistService.email(email)) return new ResponseEntity<>("电子邮箱已经存在",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

















}















































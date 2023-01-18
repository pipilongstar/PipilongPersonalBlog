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
    private final Pattern pattern = Pattern.compile("^1[3-9]\\d{9}$");

    @GetMapping("/telephone/{telephone}")
    public ResponseEntity<String> telephone(@PathVariable("telephone") String telephone){

        Matcher matcher = pattern.matcher(telephone);
        if(!matcher.matches()) return new ResponseEntity<>("手机号格式错误", HttpStatus.BAD_REQUEST);
        if(selectExistService.telephone(telephone)) return new ResponseEntity<>("手机号已经存在",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

















}















































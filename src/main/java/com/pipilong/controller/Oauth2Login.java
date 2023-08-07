package com.pipilong.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author pipilong
 * @createTime 2023/2/1
 * @description
 */
@RestController
@RequestMapping("/login/oauth2/code")
public class Oauth2Login {

    /**
     * 实现GitHub登录
     * @return
     */
    @GetMapping("/github")
    public ResponseEntity<String> github(String code){

        System.out.println(code);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

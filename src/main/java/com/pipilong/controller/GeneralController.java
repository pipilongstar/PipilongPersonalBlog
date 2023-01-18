package com.pipilong.controller;

import com.pipilong.service.GeneralOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@RestController
public class GeneralController {

    @Autowired
    private GeneralOperation generalOperation;
    /**
     * 接收手机号，发送验证码
     * @param telephone
     * @param httpSession
     */
    @PostMapping("/sendcode")
    public void sendCode(
//            @Pattern(regexp = "/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$/\n",message = "请输入正确手机号")
            String telephone,
            HttpSession httpSession){

        String sessionId = httpSession.getId();
        generalOperation.sendVerificationCode(telephone,sessionId);

    }

}
































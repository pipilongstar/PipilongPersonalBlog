package com.pipilong.controller;

import com.pipilong.service.SendMessage;
import com.pipilong.utils.VerificationCodeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@RestController
public class GeneralController {

    @Autowired
    private SendMessage sendMessage;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 接收手机号，发送验证码
     * @param telephone
     * @param httpSession
     */
    @PostMapping("/sendcode")
    public void sendCode(@Param("telephone") String telephone, HttpSession httpSession){
        String sessionId = httpSession.getId();
        String key="verificationCode:"+sessionId;

        String code = stringRedisTemplate.opsForValue().get(key);
        if(code == null) {
            code = VerificationCodeUtil.getCode();
        }

        stringRedisTemplate.opsForValue().set(key,code,300);
        sendMessage.sendSMS(telephone,code);
    }

}
































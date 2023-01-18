package com.pipilong.service.Impl;

import com.pipilong.service.GeneralOperation;
import com.pipilong.utils.ShortMessageUtil;
import com.pipilong.utils.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Service
public class GeneralOperationImpl implements GeneralOperation {

    @Autowired
    private ShortMessageUtil shortMessageUtil;
    @Autowired
    private VerificationCodeUtil verificationCodeUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendVerificationCode(String telephone, String sessionId) {
        String key="verificationCode:"+sessionId;

        String code = stringRedisTemplate.opsForValue().get(key);
        if(code == null) {
            code = verificationCodeUtil.getCode();
        }

        stringRedisTemplate.opsForValue().set(key,code,300, TimeUnit.SECONDS);
        shortMessageUtil.sendSMS(telephone,code);
    }

}



































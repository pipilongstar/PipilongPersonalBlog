package com.pipilong.service.Impl;

import com.pipilong.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author pipilong
 * @createTime 2023/1/20
 * @description
 */
@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean verificationCode(String code, String sessionId) {
        String key="verificationCode:"+sessionId;
        String saveCode = stringRedisTemplate.opsForValue().get(key);
        return saveCode == null || !saveCode.equals(code);
    }

    @Override
    public String isLogin(String sessionId) {

        String key="user:" + sessionId;
        return stringRedisTemplate.opsForValue().get(key);
    }

}
















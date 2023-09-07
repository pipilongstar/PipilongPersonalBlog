package com.pipilong.service.Impl;

import com.pipilong.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author pipilong
 * @createTime 2023/8/7
 * @description
 */
@Service
public class UtilServiceImpl implements UtilService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getToken(String sessionId) {
        String token = UUID.randomUUID().toString();

        return null;
    }

    @Override
    public boolean verifyToken(String sessionId, String token) {
        return false;
    }

}

package com.pipilong.service.Impl;

import com.pipilong.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pipilong
 * @createTime 2023/2/11
 * @description
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void systemMessage(String text, String userId) {

        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        String key = "systemMessage:"+userId;
        String value = text+":"+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        ops.add(key,value);

    }

}













package com.pipilong.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

/**
 * @author pipilong
 * @createTime 2023/1/31
 * @description
 */
@Component
@WebListener
public class SessionListener implements HttpSessionIdListener {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void sessionIdChanged(HttpSessionEvent httpSessionEvent, String oldSessionId) {

        String newSessionId = httpSessionEvent.getSession().getId();
        String oldKey = "user:" + oldSessionId;
        String newKey = "user:" + newSessionId;
        stringRedisTemplate.rename(oldKey,newKey);

    }
}

































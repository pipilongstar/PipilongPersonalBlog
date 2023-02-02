package com.pipilong.listener;

import com.pipilong.annotation.ErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

/**
 * @author pipilong
 * @createTime 2023/1/31
 * @description
 */
@Component
@Slf4j
@WebListener
public class SessionListener implements HttpSessionIdListener, HttpSessionListener {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ErrorLog
    @Override
    public void sessionIdChanged(@NotNull HttpSessionEvent httpSessionEvent, String oldSessionId) {

        String newSessionId = httpSessionEvent.getSession().getId();
        String oldKey = "user:" + oldSessionId;
        String newKey = "user:" + newSessionId;
        stringRedisTemplate.rename(oldKey,newKey);

    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        log.info("create:"+sessionId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        log.info("destroyed:"+sessionId);
    }
}

































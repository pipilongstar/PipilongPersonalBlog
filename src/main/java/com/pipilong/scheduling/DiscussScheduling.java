package com.pipilong.scheduling;

import com.pipilong.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author pipilong
 * @createTime 2023/2/8
 * @description
 */
@Component
@Slf4j
public class DiscussScheduling {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Scheduled(cron = "* * 2/23 * * ?")
    public void discussLike(){

        Set<Object> keys = stringRedisTemplate.opsForHash().keys("userLikeCount:");
        List<Object> values = stringRedisTemplate.opsForHash().values("userLikeCount:");
        Iterator<Object> iv = values.iterator();

        Iterator<Object> ik = keys.iterator();
        while(ik.hasNext()){
            userMapper.updateUserLikeCount((String) ik.next(),(String) iv.next());
        }

        stringRedisTemplate.delete("userLikeCount:");
    }

    @Scheduled(cron = "* 10 2/23 * * ?")
    public void discussCollection(){

        Set<Object> keys = stringRedisTemplate.opsForHash().keys("userCollectionCount:");
        List<Object> values = stringRedisTemplate.opsForHash().values("userCollectionCount:");
        Iterator<Object> iv = values.iterator();

        Iterator<Object> ik = keys.iterator();
        while(ik.hasNext()){
            userMapper.updateUserCollectionCount((String) ik.next(),(String) iv.next());
        }

        stringRedisTemplate.delete("userCollectionCount:");
    }

    @Scheduled(cron = "* 20 2/23 * * ?")
    public void discussRead(){

        Set<Object> keys = stringRedisTemplate.opsForHash().keys("userReadCount:");
        List<Object> values = stringRedisTemplate.opsForHash().values("userReadCount:");
        Iterator<Object> iv = values.iterator();

        Iterator<Object> ik = keys.iterator();
        while(ik.hasNext()){
            userMapper.updateUserReadCount((String) ik.next(),(String) iv.next());
        }

        stringRedisTemplate.delete("userReadCount:");
    }

}




























package com.pipilong.scheduling;

import com.pipilong.enums.MessageType;
import com.pipilong.mapper.DiscussMapper;
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
    private DiscussMapper discussMapper;
    @Autowired
    private UserMapper userMapper;

    @Scheduled(cron = "* * 2/23 * * ?")
    public void discussLike(){

        this.handle("userLikeCount:","userLikeCount:",MessageType.LIKE);
        this.handle("discussLikeCount:","discussLikeCount:",MessageType.DISCUSSLIKE);
    }

    @Scheduled(cron = "* 10 2/23 * * ?")
    public void discussCollection(){

        this.handle("userCollectionCount:","userCollectionCount:",MessageType.COLLECTION);
        this.handle("discussCollectionCount:","discussCollectionCount:",MessageType.DISCUSSCOLLECTION);
    }

    @Scheduled(cron = "* 20 2/23 * * ?")
    public void discussRead(){

        this.handle("userReadCount:","userReadCount:",MessageType.READ);
        this.handle("discussReadCount:","discussReadCount:",MessageType.DISCUSSREAD);
    }

    private void handle(String key, String value, MessageType messageType){
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(key);
        List<Object> values = stringRedisTemplate.opsForHash().values(value);
        if(keys.isEmpty()) return;
        Iterator<Object> iv = values.iterator();
        Iterator<Object> ik = keys.iterator();
        while(ik.hasNext()){
            int count= Integer.parseInt((String) iv.next()) - 1073741823;
            if(messageType==MessageType.LIKE) userMapper.updateUserLikeCount((String) ik.next(), String.valueOf(count));
            else if(messageType==MessageType.COLLECTION) userMapper.updateUserCollectionCount((String) ik.next(), String.valueOf(count));
            else if(messageType==MessageType.READ) userMapper.updateUserReadCount((String) ik.next(), String.valueOf(count));
            else if(messageType==MessageType.DISCUSSLIKE) discussMapper.updateDiscussLikeCount((String) ik.next(), String.valueOf(count));
            else if(messageType==MessageType.DISCUSSCOLLECTION) discussMapper.updateDiscussCollectionCount((String) ik.next(), String.valueOf(count));
            else if(messageType==MessageType.DISCUSSREAD) discussMapper.updateDiscussReadCount((String) ik.next(), String.valueOf(count));
        }

        stringRedisTemplate.delete(key);
    }

}




























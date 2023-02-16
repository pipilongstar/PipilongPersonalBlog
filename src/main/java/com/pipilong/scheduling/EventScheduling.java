package com.pipilong.scheduling;

import com.pipilong.annotation.ErrorLog;
import com.pipilong.enums.MessageType;
import com.pipilong.mapper.EventMapper;
import com.pipilong.mapper.UserMapper;
import com.pipilong.pojo.PersonMessage;
import com.pipilong.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author pipilong
 * @createTime 2023/2/11
 * @description
 */
@Component
public class EventScheduling {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CodeGenerator codeGenerator;

    @Scheduled(fixedRate = 30*60*1000)
    @ErrorLog
    public void systemMessage() {

        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        Set<String> keys = stringRedisTemplate.keys("systemMessage:*");
        if(keys == null || keys.isEmpty()) return;
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Set<String> values = ops.members(key);
            assert values != null;
            Iterator<String> iValues = values.iterator();
            String userId = key.substring(key.lastIndexOf(':') + 1);
            //将全部用户的id取出，插入表中

            while (iValues.hasNext()) {
                String value = iValues.next();
                String text = value.substring(0, value.indexOf(':'));
                String date = value.substring(value.indexOf(':') + 1);
                if("全体".equals(userId)){

                    List<String> userIds = userMapper.selectUserId();
                    for (String userid : userIds) {
                        eventMapper.insertSystemMessage(text, userid, date);
                    }

                }else eventMapper.insertSystemMessage(text, userId, date);
            }

            stringRedisTemplate.delete(key);
        }
    }

    @Scheduled(fixedRate = 30*60*1000)
    @ErrorLog
    public void likeMessage(){

        HashOperations<String, Object, Object> ops = stringRedisTemplate.opsForHash();
        Set<String> keys = stringRedisTemplate.keys("likeMessage:*");
        if(keys == null || keys.isEmpty()) return;
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()){

            String key = iterator.next();
            String discussId = key.substring(key.lastIndexOf(':')+1);

            PersonMessage message = new PersonMessage();
            message.setMessageType(MessageType.LIKE);
            message.setMessageId(codeGenerator.getCode(8));
            message.setUserId((String) ops.get(key,"authorId"));
            message.setIsRead(false);

            message.setEventSourceUrl((String) ops.get(key,"eventSourceUrl"));
            message.setEventSourceId(discussId);
            message.setText("<span style=\"#5b91d9\">"+(String) ops.get(key,"username")+"</span>"+ "等"
                    +ops.get(key,"count")+"人 赞了你发布的讨论 "+"<span style=\"color = #5b91d9\">"+ops.get(key,"discussTheme")+"</span>");
            message.setDate((String) ops.get(key,"date"));

            eventMapper.insertPersonMessage(message);
            stringRedisTemplate.delete(key);
        }

    }

}

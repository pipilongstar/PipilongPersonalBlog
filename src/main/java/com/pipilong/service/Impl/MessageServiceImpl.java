package com.pipilong.service.Impl;

import com.alibaba.fastjson.JSON;
import com.pipilong.annotation.ErrorLog;
import com.pipilong.enums.MessageType;
import com.pipilong.mapper.MessageMapper;
import com.pipilong.pojo.*;
import com.pipilong.pojo.Message;
import com.pipilong.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author pipilong
 * @createTime 2023/2/14
 * @description
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @ErrorLog(true)
    public List<Object> getChatRoom(String userId) {

        List<ChatRoom> chatRooms = messageMapper.getChatRoom(userId);
        int count =0;
        for(ChatRoom chatRoom: chatRooms){
            count+=Integer.parseInt(chatRoom.getNoReadCount());
        }
        return Arrays.asList(JSON.toJSONString(chatRooms),count);
    }

    @Override
    @Cacheable(value = "chatRecord",key = "#userId + ':' +  #friendId")
    public List<ChatRecord> getChatRecord(String userId, String friendId) {
        List<ChatRecord> chatRecord = messageMapper.getChatRecord(userId, friendId);
        Collections.reverse(chatRecord);
        messageMapper.updateNoReadCount(userId,friendId);
        return chatRecord;
    }

    @Override
    @ErrorLog(true)
    @Cacheable(value = "message",key = "'system'+#userId")
    public List<Object> getSystemMessage(String userId) {

        List<SystemMessage> message = messageMapper.getSystemMessage(userId);
        Integer count = calculate(message);
        messageMapper.updateNoReadSystemMessage(userId);

        return Arrays.asList(JSON.toJSONString(message),count);
    }

    @Override
    @ErrorLog(true)
    @Cacheable(value = "message",key = "'like'+#userId")
    public List<Object> getLikeMessage(String userId){

        List<PersonMessage> message = messageMapper.getPersonMessage(userId, MessageType.LIKE);
        Integer count = calculate(message);

        return Arrays.asList(JSON.toJSONString(message),count);
    }

    @Override
    @ErrorLog(true)
    @Cacheable(value = "message",key = "'comment'+#userId")
    public List<Object> getCommentMessage(String userId) {

        List<PersonMessage> message = messageMapper.getPersonMessage(userId, MessageType.COMMENT);
        List<PersonMessage> message1 = messageMapper.getPersonMessage(userId, MessageType.AT);
        if(message==null) message=message1;
        else message.addAll(message1);
        Integer count=calculate(message);

        return Arrays.asList(JSON.toJSONString(message),count);
    }

    @Override
    @ErrorLog(true)
    @Cacheable(value = "message",key = "'follow'+#userId")
    public List<Object> getFollowMessage(String userId) {

        List<PersonMessage> message = messageMapper.getPersonMessage(userId, MessageType.FOLLOW);
        Integer count = calculate(message);

        return Arrays.asList(JSON.toJSONString(message),count);
    }

    @Override
    public void deleteChatRoom(String userId, String friendId) {

        messageMapper.deleteChatRoom(userId,friendId);
    }

    @Override
    @ErrorLog
    public void updateNoRead(String userId, String type) {
        assert MessageType.getInstance(type) == null : new NullPointerException();
        messageMapper.updateNoReadMessage(userId,MessageType.getInstance(type));
        String key = "message::"+ Objects.requireNonNull(MessageType.getInstance(type)).getLowerCase()+userId;
        stringRedisTemplate.delete(key);
    }

    private Integer calculate(List<? extends Message> message){
        if(message.isEmpty()) return 0;
        int count=0;
        for(Message m:message){
            count += m.getIsRead() ? 0 : 1;
        }
        return count;
    }

}















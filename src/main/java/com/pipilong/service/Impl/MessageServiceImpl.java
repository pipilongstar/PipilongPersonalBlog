package com.pipilong.service.Impl;

import com.alibaba.fastjson.JSON;
import com.pipilong.annotation.ErrorLog;
import com.pipilong.enums.MessageType;
import com.pipilong.mapper.MessageMapper;
import com.pipilong.pojo.*;
import com.pipilong.pojo.Abstract.Message;
import com.pipilong.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Override
    @ErrorLog(true)
    public List<ChatRoom> getChatRoom(String userId) {

        return messageMapper.getChatRoom(userId);
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
        message.addAll(messageMapper.getPersonMessage(userId,MessageType.AT));
        Integer count = calculate(message);

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

    private Integer calculate(@NotNull List<? extends Message> message){
        int count=0;
        for(Message m:message){
            count += m.getIsRead() ? 0 : 1;
        }
        return count;
    }

}















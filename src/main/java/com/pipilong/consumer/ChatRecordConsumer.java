package com.pipilong.consumer;

import com.pipilong.annotation.ErrorLog;
import com.pipilong.mapper.MessageMapper;
import com.pipilong.mapper.UserMapper;
import com.pipilong.pojo.ChatRecord;
import com.pipilong.pojo.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/15
 * @description
 */
@Component
@Slf4j
public class ChatRecordConsumer {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @ErrorLog
    @RabbitListener(queues = "chatRecordQueue")
    public void consumer(List<Object> data){
//        ChatRecord chatRecord = (ChatRecord) data.get(0);
//        Boolean isRead = (Boolean) data.get(1);
//        //保存到自己的聊天记录中，只有发消息的时候会保存记录，且是双方记录
//        String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
//        chatRecord.setDate(date);
//        messageMapper.insertRecord(chatRecord);
//        messageMapper.updateChatRoom(chatRecord.getUserId(),date, chatRecord.getText(), "0", chatRecord.getFriendId());
//
//        //保存到对方的聊天记录中
//        String userId = chatRecord.getUserId();
//        String friendId = chatRecord.getFriendId();
//        chatRecord.setUserId(friendId);
//        chatRecord.setFriendId(userId);
//        chatRecord.setPosition("left");
//        messageMapper.insertRecord(chatRecord);
//        Integer existChatRoom = messageMapper.isExistChatRoom(friendId, userId);
//        if(existChatRoom == null) {
//            ChatRoom chatRoom = new ChatRoom();
//            chatRoom.setFriendId(userId);
//            chatRoom.setFriendName(userMapper.getUserNameByUserId(userId));
//            chatRoom.setUserAvatarUrl("https://cdn.pipilong.com.cn/UserAvatar/" + userId + ".jpg");
//            chatRoom.setDate(date);
//            messageMapper.insertRoom(chatRoom, friendId);
//        } else messageMapper.updateChatRoom(friendId,date, chatRecord.getText(), isRead ? "0":"1",userId);
//
////      保存完后把双方的redis删除，实现缓存一致性
//        String key1 = "chatRecord::"+chatRecord.getUserId()+":"+chatRecord.getFriendId();
//        String key2 = "chatRecord::"+chatRecord.getFriendId()+":"+chatRecord.getUserId();
//        stringRedisTemplate.delete(Arrays.asList(key1,key2));
    }

}

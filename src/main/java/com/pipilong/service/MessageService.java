package com.pipilong.service;

import com.pipilong.pojo.ChatRecord;
import com.pipilong.pojo.ChatRoom;
import com.pipilong.pojo.PersonMessage;
import com.pipilong.pojo.SystemMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/14
 * @description
 */
@Service
public interface MessageService {

    /**
     * 获取聊天室信息
     * @param userId 用户id
     * @return 聊天室信息
     */
    List<Object> getChatRoom(String userId);

    /**
     * 获取聊天记录
     * @param userId 用户id
     * @param friendId 好友id
     * @return 聊天记录
     */
    List<ChatRecord> getChatRecord(String userId,String friendId);

    /**
     * 获取用户系统消息
     * @param userId 用户id
     * @return 系统消息列表
     */
    List<Object> getSystemMessage(String userId);

    /**
     * 获取用户点赞消息
     * @param userId 用户id
     * @return 点赞消息列表
     */
    List<Object> getLikeMessage(String userId);

    /**
     * 获取用户评论和@消息
     * @param userId 用户id
     * @return 评论消息列表
     */
    List<Object> getCommentMessage(String userId);

    /**
     * 获取新增关注信息
     * @param userId 用户id
     * @return 关注消息列表
     */
    List<Object> getFollowMessage(String userId);

    /**
     * 删除用户聊天室
     * @param userId 用户id
     * @param friendId 好友id
     */
    void deleteChatRoom(String userId,String friendId);

    /**
     * 更新个人信息未读
     * @param userId 用户id
     * @param type 信息类型
     */
    void updateNoRead(String userId,String type);
}


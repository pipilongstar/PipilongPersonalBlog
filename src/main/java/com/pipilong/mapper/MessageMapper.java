package com.pipilong.mapper;

import com.pipilong.enums.MessageType;
import com.pipilong.pojo.ChatRecord;
import com.pipilong.pojo.ChatRoom;
import com.pipilong.pojo.PersonMessage;
import com.pipilong.pojo.SystemMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/14
 * @description
 */
@Mapper
public interface MessageMapper {

    /**
     * 获取聊天室信息
     * @param userId 用户id
     * @return 得到的聊天室信息
     */
    List<ChatRoom> getChatRoom(@Param("userid") String userId);

    /**
     * 聊天记录信息
     * @param userId 用户id
     * @return 得到的聊天记录信息
     */
    List<ChatRecord> getChatRecord(@Param("userid") String userId,@Param("friendId") String friendId);

    /**
     * 向数据库中插入聊天记录
     * @param chatRecord 聊天记录信息
     */
    void insertRecord(@Param("chat") ChatRecord chatRecord);

    /**
     * 读取用户的系统消息
     * @param userId 用户id
     * @return 该用户的系统消息集
     */
    List<SystemMessage> getSystemMessage(@Param("userid") String userId);

    /**
     * 读取用户的个人信息
     * @param userId 用户id
     * @param messageType 信息类型
     * @return 信息列表
     */
    List<PersonMessage> getPersonMessage(@Param("userid") String userId,@Param("messageType") MessageType messageType);

    /**
     * 查询该用户是否有该聊天室
     * @param userId 用户id
     * @return true or false
     */
    Integer isExistChatRoom(@Param("userid") String userId,@Param("friendid") String friendId);

    /**
     * 插入聊天室
     * @param chatRoom 聊天室信息
     */
    void insertRoom(@Param("room") ChatRoom chatRoom,@Param("userid") String userid);

    /**
     * 更新聊天室信息
     * @param userId 用户id
     * @param date 日期
     * @param text 内容
     * @param count 未读数
     */
    void updateChatRoom(@Param("userid") String userId,@Param("date") String date,@Param("text") String text,@Param("count") String count,@Param("friendid") String friendId);

    /**
     * 删除聊天室
     * @param userId 用户id
     * @param friendId 好友id
     */
    void deleteChatRoom(@Param("userid")String userId,@Param("friendid") String friendId);

    /**
     * 更新未读消息数
     * @param userId 用户id
     * @param friendId 好友id
     */
    void updateNoReadCount(@Param("userid")String userId,@Param("friendid") String friendId);
}
























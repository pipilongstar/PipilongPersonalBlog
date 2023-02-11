package com.pipilong.mapper;

import com.pipilong.pojo.PersonMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author pipilong
 * @createTime 2023/2/11
 * @description
 */
@Mapper
public interface EventMapper {

    /**
     * 插入系统消息
     * @param text 消息内容
     * @param userId 用户id
     * @param date 消息发送的日期
     */
    void insertSystemMessage(@Param("text") String text,@Param("userid") String userId,@Param("date") String date);


    /**
     * 插入用户消息
     * @param personMessage 用户消息信息
     */
    void insertPersonMessage(@Param("message") PersonMessage personMessage);





}

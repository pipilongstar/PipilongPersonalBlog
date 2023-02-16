package com.pipilong.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/14
 * @description
 */
@Component
@Data
public class ChatRoom implements Serializable {


    private String userAvatarUrl;
    private String friendName;
    private String friendId;
    private String text;
    private String date;
    private List<ChatRecord> chatRecords;
    private String noReadCount;

}
















package com.pipilong.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author pipilong
 * @createTime 2023/2/14
 * @description
 */
@Component
@Data
public class ChatRecord implements Serializable {

    private String userId;
    private String friendId;
    private String userAvatarUrl;
    private String text;
    private String position;
    private String date;

}

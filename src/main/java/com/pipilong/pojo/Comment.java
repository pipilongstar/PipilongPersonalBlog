package com.pipilong.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/2/9
 * @description 评论表
 */
@Component
@Data
public class Comment {

    private Long id;
    private String discussId;
    private String userAvatarUrl;
    private String username;
    private String replyId;
    private String date;
    private String text;
    private String userId;
    private String authorId;
    private String discussUrl;
    private String discussTheme;

}

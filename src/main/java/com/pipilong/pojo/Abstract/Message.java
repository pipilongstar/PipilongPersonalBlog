package com.pipilong.pojo.Abstract;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/2/16
 * @description
 */
@Data
@Component
public abstract class Message {


    protected String userId;
    protected String date;
    protected String text;
    protected Boolean isRead;


}

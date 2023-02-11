package com.pipilong.pojo;

import com.pipilong.enums.MessageType;
import lombok.Data;
import org.elasticsearch.common.inject.Singleton;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/2/11
 * @description
 */
@Component
@Data
public class PersonMessage {

    private Long id;
    private String userId;
    private Enum<MessageType> messageType;
    private String date;
    private String eventSourceId;
    private String text;
    private String eventSourceUrl;
    private String messageId;
    private Boolean isRead;

}





















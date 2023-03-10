package com.pipilong.pojo;

import com.pipilong.enums.MessageType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author pipilong
 * @createTime 2023/2/11
 * @description
 */
@Component
@Data
public class PersonMessage extends Message implements Serializable {

    private Long id;
    private MessageType messageType;
    private String eventSourceId;
    private String eventSourceUrl;
    private String messageId;

}





















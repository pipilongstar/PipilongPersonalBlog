package com.pipilong.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/2/18
 * @description
 */
@Component
@Data
public class Follow {


    private String userAvatarUrl;
    private String username;
    private String text;
    private String userId;


}

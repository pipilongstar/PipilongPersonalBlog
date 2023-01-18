package com.pipilong.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Data
@Component
public class User {

    private String userId;
    private String userName;
    private String email;
    private String telephone;

}

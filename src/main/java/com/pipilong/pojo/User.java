package com.pipilong.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
    private String password;

}

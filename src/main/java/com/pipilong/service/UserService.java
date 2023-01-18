package com.pipilong.service;

import com.pipilong.pojo.User;
import com.sun.deploy.association.RegisterFailedException;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Service
public interface UserService {

    /**
     * 注册用户
     * @param user 用户信息
     * @return 返回用户id
     */
    String register(User user, String sessionId) throws RegisterFailedException;



}

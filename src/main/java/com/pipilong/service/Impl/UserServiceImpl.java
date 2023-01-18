package com.pipilong.service.Impl;

import com.pipilong.mapper.UserMapper;
import com.pipilong.pojo.User;
import com.pipilong.service.UserService;
import com.pipilong.utils.CodeGenerator;
import com.sun.deploy.association.RegisterFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CodeGenerator codeGenerator;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    @Transactional
    public String register(User user, String sessionId) throws RegisterFailedException {
        String userId = codeGenerator.getCode(8);
        boolean isSuccess1 = userMapper.registerUserToSecurity(userId, user.getEmail(), user.getTelephone());
        boolean isSuccess2 = userMapper.registerUserToData(userId, user.getUserName());
        if(!(isSuccess1 && isSuccess2)) throw new RegisterFailedException("服务器异常");
        String key="user:"+sessionId;
        stringRedisTemplate.opsForValue().set(key,userId);
        return userId;
    }
}

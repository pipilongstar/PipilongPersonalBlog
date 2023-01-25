package com.pipilong.service;

import org.springframework.stereotype.Service;

/**
 * @author pipilong
 * @createTime 2023/1/19
 * @description
 */
@Service
public interface SelectExistService {

    /**
     * 查询数据库，判断手机号是否存在
     * @param telephone 手机号
     * @return true or false
     */
    boolean telephone(String telephone);

    /**
     * 查询数据库，判断电子邮箱是否已经存在
     * @param email 电子邮箱
     * @return  true or false
     */
    boolean email(String email);

    /**
     * 查询数据库，判断密码是否已经设置
     * @param userId 用户id
     * @return true or false
     */
    boolean password(String userId);

}

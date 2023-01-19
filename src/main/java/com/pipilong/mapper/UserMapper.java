package com.pipilong.mapper;

import com.pipilong.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Mapper
public interface UserMapper {

    /**
     * 把用户相关信息注册到account_security表中
     * @param userId 用户id
     * @param email 电子邮箱
     * @param telephone 手机号
     */
    void registerUserToSecurity(@Param("userId") String userId, @Param("email") String email,@Param("telephone") String telephone);

    /**
     * 把用户相关信息注册到user_data表中
     * @param userId 用户id
     * @param username 用户名
     */
    void registerUserToData(@Param("userId") String userId,@Param("username") String username);

    /**
     * 根据手机号查找用户id
     * @param telephone 用户手机号
     * @return userId
     */
    String getUserIdByTelephone(@Param("telephone") String telephone);

    /**
     * 根据手机号或者电子邮件查找密码
     * @param identifier 手机号或者电子邮件
     * @return 用户信息
     */
    User getPasswordByIdentifier(@Param("identifier") String identifier);
}

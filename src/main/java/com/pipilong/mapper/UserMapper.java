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

    /**
     * 修改个人简介信息
     * @param user 用户信息
     * @return true or false
     */
    void modifyProfile(@Param("user") User user);

    /**
     * 根据用户id查找手机号
     * @param userId 用户id
     * @return 手机号
     */
    String selectTelephoneByUserId(@Param("userid") String userId);

    /**
     * 修改手机号
     * @param telephone 新手机号
     */
    void modifyTelephone(@Param("telephone") String telephone,@Param("userid") String userId);

    /**
     * 根据用户id找邮箱
     * @param userId 用户id
     */
    String selectEmailByUserIdAfter(@Param("userid") String userId);
}















package com.pipilong.mapper;

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

    boolean registerUserToSecurity(@Param("userId") String userId, @Param("email") String email,@Param("telephone") String telephone);

    boolean registerUserToData(@Param("userId") String userId,@Param("username") String username);

}

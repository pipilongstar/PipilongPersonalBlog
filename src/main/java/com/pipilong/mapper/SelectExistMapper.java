package com.pipilong.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author pipilong
 * @createTime 2023/1/19
 * @description
 */
@Mapper
public interface SelectExistMapper {

    /**
     * 查询数据库，判断手机号是否已经存在
     * @param telephone 手机号
     * @return true or false
     */
    boolean telephone(@Param("telephone") String telephone);

    /**
     * 查询数据库，判断电子邮箱是否已经存在
     * @param email 电子邮箱
     * @return true or false
     */
    boolean email(@Param("email") String email);

    /**
     * 根据userid判断密码是否已经设置
     * @param userId 用户id
     * @return true or false
     */
    String password(@Param("userid") String userId);

    /**
     * 查询githubId是否已经存在
     * @param githubId githubId号
     * @return true or false
     */
    Integer githubIdIfExist(String githubId);
}

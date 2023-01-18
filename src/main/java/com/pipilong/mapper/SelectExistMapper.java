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


    boolean telephone(@Param("telephone") String telephone);


}

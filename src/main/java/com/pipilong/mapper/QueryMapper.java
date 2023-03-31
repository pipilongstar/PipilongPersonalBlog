package com.pipilong.mapper;

import com.pipilong.pojo.Discuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/9
 * @description
 */
@Mapper
public interface QueryMapper {

    List<Discuss> selectRankingList(@Param("limit")Integer limit);

    Discuss selectByDiscussId(@Param("discussId")String discussId);


}

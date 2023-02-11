package com.pipilong.mapper;

import com.pipilong.pojo.Discuss;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/9
 * @description
 */
@Mapper
public interface QueryMapper {


    List<Discuss> selectRankingList();






}

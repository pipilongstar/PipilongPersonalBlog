package com.pipilong.service;

import com.pipilong.pojo.Discuss;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/9
 * @description
 */
@Service
public interface QueryService {


    List<Discuss> selectRankingList();

}

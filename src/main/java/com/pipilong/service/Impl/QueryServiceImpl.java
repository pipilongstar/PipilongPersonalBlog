package com.pipilong.service.Impl;

import com.pipilong.mapper.QueryMapper;
import com.pipilong.pojo.Discuss;
import com.pipilong.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/9
 * @description
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    private QueryMapper queryMapper;

    @Override
    @Cacheable(value = "redisCacheManager",key = "'rankingList'")
    public List<Discuss> selectRankingList() {

        List<Discuss> list = queryMapper.selectRankingList();
        for(Discuss discuss : list){
            discuss.setUserAvatarUrl("https://cdn.pipilong.com.cn/UserAvatar/"+discuss.getUserId()+".jpg");
        }

        return list;
    }















}

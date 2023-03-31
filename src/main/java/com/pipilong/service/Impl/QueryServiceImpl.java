package com.pipilong.service.Impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.hash.BloomFilter;
import com.pipilong.mapper.QueryMapper;
import com.pipilong.pojo.Discuss;
import com.pipilong.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author pipilong
 * @createTime 2023/2/9
 * @description
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    private QueryMapper queryMapper;

    @Autowired
    private Cache<String,List<Discuss>> cache;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Discuss> selectRankingList() {

        List<Discuss> hotListData =cache.getIfPresent("hotList");
        if(hotListData!=null) return hotListData;

        List<Discuss> list = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<String>> hotList = stringRedisTemplate.opsForZSet().reverseRangeWithScores("hotList", 0, 5);
        assert hotList != null;
        for (ZSetOperations.TypedTuple<String> s : hotList) {
            list.add(queryMapper.selectByDiscussId(s.getValue()));
        }
        if(list.size()<6){
            List<Discuss> list1 = queryMapper.selectRankingList(6 - list.size());
            list.addAll(list1);
        }
//
        for(Discuss discuss : list){
            discuss.setUserAvatarUrl("https://cdn.pipilong.com.cn/UserAvatar/"+discuss.getUserId()+".jpg");
        }

        cache.put("hotList",list);
        return list;
    }















}

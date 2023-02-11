package com.pipilong.service;

import com.pipilong.pojo.Discuss;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author pipilong
 * @createTime 2023/2/4
 * @description
 */
@Service
public interface SubmitElasticSearchService {

    /**
     * 提交用户讨论到es上
     * @param discuss 用户的讨论
     */
    void submitDiscuss(Discuss discuss) throws IOException;


    /**
     * 根据相关条件到es上查询数据
     * @param conditional 条件
     * @return 查询到的数据
     */
    String query(String conditional) throws IOException, JSONException;

    /**
     * 搜索显示高亮
     * @param conditional 搜索条件
     * @return 搜索内容
     */
    String search(String conditional) throws IOException, JSONException;

}
































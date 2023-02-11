package com.pipilong.service.Impl;

import com.alibaba.fastjson.JSON;
import com.pipilong.annotation.ErrorLog;
import com.pipilong.pojo.Discuss;
import com.pipilong.service.SubmitElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/4
 * @description
 */
@Service
@Slf4j
public class SubmitElasticSearchServiceImpl implements SubmitElasticSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private final Request request=new Request("GET", "/discuss/_search");

//    @Async
    public void submitDiscuss(@NotNull Discuss discuss) throws IOException {

        Long userId = discuss.getUserId();
        String userAvatarUrl="https://cdn.pipilong.com.cn/UserAvatar/"+userId+".jpg";
        discuss.setUserAvatarUrl(userAvatarUrl);

        IndexRequest indexRequest = new IndexRequest("discuss");
        indexRequest.id(discuss.getDiscussId().toString());
        indexRequest.source(JSON.toJSONString(discuss), XContentType.JSON);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            log.info(e.getMessage());
        }

    }

    @ErrorLog(true)
    @Override
    public String query(String conditional) throws IOException, JSONException {

        this.request.setJsonEntity(conditional);
        Response response = restHighLevelClient.getLowLevelClient().performRequest(this.request);
        InputStream inputStream = response.getEntity().getContent();
        int length = inputStream.available();
        byte[] bytes=new byte[length];
        int read = inputStream.read(bytes);
        String responseBody=new String(bytes);

        return parseJsonData(responseBody).toString();
    }

    @Override
    public String search(String conditional) throws IOException, JSONException {
        this.request.setJsonEntity(conditional);
        Response response = restHighLevelClient.getLowLevelClient().performRequest(this.request);
        InputStream inputStream = response.getEntity().getContent();
        int length = inputStream.available();
        byte[] bytes=new byte[length];
        int read = inputStream.read(bytes);
        String responseBody=new String(bytes);

        return parseHighLight(responseBody);
    }

    @ErrorLog
    private String parseHighLight(String responseBody) throws JSONException {
        List<String> list = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray hits1 = hits.getJSONArray("hits");
        for(int i=0;i<hits1.length();i++){
            JSONObject source = hits1.getJSONObject(i).getJSONObject("_source");
            String dis = hits1.getJSONObject(i).getJSONObject("highlight").getJSONArray("theme").get(0).toString();

            source.put("theme",dis);
            list.add(source.toString());
        }

        return list.toString();
    }

    @ErrorLog
    private List<String> parseJsonData(String data) throws JSONException {
        List<String> list = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray hits1 = hits.getJSONArray("hits");
        for(int i=0;i<hits1.length();i++){
            list.add(hits1.getJSONObject(i).getString("_source"));
        }

        return list;
    }
}





































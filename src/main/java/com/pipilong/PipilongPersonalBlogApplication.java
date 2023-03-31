package com.pipilong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.hash.BloomFilter;
import com.pipilong.annotation.ErrorLog;
import com.pipilong.service.Impl.SubmitElasticSearchServiceImpl;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableScheduling
@EnableAspectJAutoProxy
@EnableAsync
@EnableWebSocket
@EnableConfigurationProperties
public class PipilongPersonalBlogApplication {

    @Autowired
    private BloomFilter<String> bloomFilter;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private SubmitElasticSearchServiceImpl service;

    private final Request request=new Request("GET", "/discuss/_search");

    public static void main(String[] args) {
        SpringApplication.run(PipilongPersonalBlogApplication.class, args);

    }

    @ErrorLog
    @PostConstruct
    public void init() throws IOException {
        List<String> keys = this.getAllKeysFromElasticsearch();
        for(String key : keys){
            List<String> queryItems = service.analyzeQuery(key);
            for(String item : queryItems){
                this.bloomFilter.put(item);
            }
        }
    }

    @NotNull
    private List<String> getAllKeysFromElasticsearch() throws IOException {
        String conditional="{\n" +
                "    \"query\": {\n" +
                "        \"match_all\": {}\n" +
                "    }\n" +
                "}";
        request.setJsonEntity(conditional);
        Response response = restHighLevelClient.getLowLevelClient().performRequest(this.request);
        InputStream is = response.getEntity().getContent();
        int length = is.available();
        byte[] bytes=new byte[length];
        int size = is.read(bytes);
        return parseJsonData(new String(bytes));
    }

    @NotNull
    private List<String> parseJsonData(String data) {
        List<String> res=new ArrayList<>();
        JSONArray array = JSON.parseObject(data).getJSONObject("hits").getJSONArray("hits");
        for(int i=0;i<array.size();i++){
            res.add(array.getJSONObject(i).getJSONObject("_source").getString("theme"));
        }
        return res;
    }


}


































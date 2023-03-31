package com.pipilong.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author pipilong
 * @createTime 2023/2/5
 * @description
 */
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${es.hostAndPort}")
    private String hostAndPort;

    @Override
    @NotNull
    @Bean
    public RestHighLevelClient elasticsearchClient(){
        ClientConfiguration configuration = ClientConfiguration.builder().connectedTo(hostAndPort).build();
        return RestClients.create(configuration).rest();
    }

}




















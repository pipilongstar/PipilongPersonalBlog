package com.pipilong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@Configuration
public class RedisConfig {

    @Bean
    public CacheManager initRedisCacheManager(
            @Autowired RedisConnectionFactory redisConnectionFactory
    ){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheManager redisCacheManager=
                RedisCacheManager.RedisCacheManagerBuilder
                        .fromConnectionFactory(redisConnectionFactory)
                        .withCacheConfiguration("user:",config)
                        .build();
        return redisCacheManager;
    }

}

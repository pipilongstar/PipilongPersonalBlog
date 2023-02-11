package com.pipilong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@Configuration
public class RedisConfig {
    @Bean("redisCacheManager")
    public CacheManager initRedisCacheManager(
            @Autowired RedisConnectionFactory redisConnectionFactory
    ){
        RedisSerializationContext.SerializationPair<Object> valueSerialization = RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java());

        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofDays(1L))
                .disableCachingNullValues()
                .serializeValuesWith(valueSerialization);
        RedisCacheManager redisCacheManager=
                RedisCacheManager.RedisCacheManagerBuilder
                        .fromConnectionFactory(redisConnectionFactory)
                        .withCacheConfiguration("redisCacheManager",config)
                        .build();
        return redisCacheManager;
    }

}

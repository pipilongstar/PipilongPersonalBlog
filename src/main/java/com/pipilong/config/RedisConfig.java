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
import java.util.HashMap;

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

        HashMap<String, RedisCacheConfiguration> map = new HashMap<>();
        RedisCacheConfiguration config1 = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofDays(1L))
                .disableCachingNullValues()
                .serializeValuesWith(valueSerialization);
        map.put("hotList",config1);
        RedisCacheConfiguration config2 = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10L))
                .disableCachingNullValues()
                .serializeValuesWith(valueSerialization);
        map.put("chatRecord",config2);
        RedisCacheConfiguration config3 = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10L))
                .disableCachingNullValues()
                .serializeValuesWith(valueSerialization);
        map.put("message",config3);

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .withInitialCacheConfigurations(map)
                .build();
    }

}

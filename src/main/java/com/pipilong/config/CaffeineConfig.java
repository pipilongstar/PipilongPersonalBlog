package com.pipilong.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.pipilong.pojo.Discuss;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author pipilong
 * @createTime 2023/2/26
 * @description
 */
@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<String, List<Discuss>> caffeineCache(){
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.DAYS)
                .recordStats()
                .build();
    }

}

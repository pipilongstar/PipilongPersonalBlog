package com.pipilong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })
@EnableCaching
@EnableTransactionManagement
@EnableScheduling
public class PipilongPersonalBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PipilongPersonalBlogApplication.class, args);
    }

}

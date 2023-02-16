package com.pipilong;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableScheduling
@EnableAspectJAutoProxy
@EnableAsync
@EnableWebSocket
@EnableConfigurationProperties
public class PipilongPersonalBlogApplication {

//    @Autowired
//    private RabbitTemplate rabbitTemplate;
    public static void main(String[] args) {
        SpringApplication.run(PipilongPersonalBlogApplication.class, args);

    }

//    @Override
//    public void run(String... args) throws Exception {
//        rabbitTemplate.convertAndSend("chatRecordExchange","chatRecord","你好");
//    }
}

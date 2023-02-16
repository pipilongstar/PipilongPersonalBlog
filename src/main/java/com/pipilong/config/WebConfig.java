package com.pipilong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@Configuration
public class WebConfig {

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}

































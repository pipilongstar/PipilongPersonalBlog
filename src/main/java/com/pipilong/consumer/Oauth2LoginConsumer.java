package com.pipilong.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author pipilong
 * @createTime 2023/2/27
 * @description
 */
@Component
public class Oauth2LoginConsumer {



    @RabbitListener(queues = "githubQueue")
    public void github(Map<String,String> userData){


    }

    @RabbitListener(queues = "giteeQueue")
    public void gitee(Map<String,String> userData){

        System.out.println(userData);
    }

}

package com.pipilong.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pipilong
 * @createTime 2023/2/14
 * @description
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {


    /**
     * 发送聊天记录的队列
     * @return 实例化队列
     */
    @Bean
    public Queue chatRecordQueue(){
        return new Queue("chatRecordQueue",true);
    }

    /**
     * 发送聊天记录的交换机
     * @return 实例化交换机
     */
    @Bean
    public DirectExchange chatRecordExchange(){
        return new DirectExchange("chatRecordExchange",true,false);
    }

    @Bean
    public Binding chatRecordQueueToExchange(){
        return BindingBuilder.bind(chatRecordQueue()).to(chatRecordExchange()).with("chatRecord");
    }

}

package com.pipilong.config;

import org.springframework.amqp.core.*;
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

    /**
     * 注册时的交换机
     * @return 实例化的交换机
     */
    @Bean
    public FanoutExchange registerExchange(){

        return new FanoutExchange("registerExchange",true,false);
    }

    @Bean
    public DirectExchange discussExchange(){

        return new DirectExchange("discussExchange",true,false);
    }

    @Bean
    public Queue smsQueue(){

        return new Queue("smsQueue",true);
    }

    @Bean
    public Queue emailQueue(){

        return new Queue("emailQueue",true);
    }

    @Bean
    public Queue likeQueue(){

        return new Queue("likeQueue",true);
    }

    @Bean
    public Queue collectionQueue(){

        return new Queue("collectionQueue",true);
    }

    @Bean
    public Queue readQueue(){

        return new Queue("readQueue",true);
    }

    @Bean
    public Binding likeQueueToDiscussExchange(){

        return BindingBuilder.bind(likeQueue()).to(discussExchange()).with("like");
    }

    @Bean
    public Binding collectionQueueToDiscussExchange(){

        return BindingBuilder.bind(collectionQueue()).to(discussExchange()).with("collection");
    }

    @Bean
    public Binding readQueueToDiscussExchange(){

        return BindingBuilder.bind(readQueue()).to(discussExchange()).with("read");
    }
    @Bean
    public Binding smsQueueToRegisterExchange(){

        return BindingBuilder.bind(smsQueue()).to(registerExchange());
    }
    @Bean
    public Binding emailQueueToRegisterExchange(){

        return BindingBuilder.bind(emailQueue()).to(registerExchange());
    }
    @Bean
    public Binding chatRecordQueueToExchange(){
        return BindingBuilder.bind(chatRecordQueue()).to(chatRecordExchange()).with("chatRecord");
    }


    /**
     * 用来计算用户行为的队列
     */
    @Bean
    public Queue scoreCalculationQueue(){
        return new Queue("scoreCalculationQueue",true);
    }

    @Bean
    public DirectExchange scoreCalculationExchange(){
        return new DirectExchange("scoreCalculationExchange",true,false);
    }

    @Bean
    public Binding scoreCalculationQueueToExchange(){
        return BindingBuilder.bind(scoreCalculationQueue()).to(scoreCalculationExchange()).with("scoreCalculation");
    }

    /**
     * 用来存储github登录的用户的信息
     * @return null
     */
    @Bean
    public Queue githubQueue(){
        return new Queue("githubQueue",true);
    }

    @Bean
    public Queue giteeQueue(){
        return new Queue("giteeQueue",true);
    }

    @Bean
    public DirectExchange oauth2LoginExchange(){
        return new DirectExchange("oauth2LoginExchange",true,false);
    }

    @Bean
    public Binding githubQueueToExchange(){
        return BindingBuilder.bind(githubQueue()).to(oauth2LoginExchange()).with("github");
    }

    @Bean
    public Binding giteeQueueToExchange(){
        return BindingBuilder.bind(giteeQueue()).to(oauth2LoginExchange()).with("gitee");
    }
}



























package com.pipilong.consumer;

import com.pipilong.enums.MessageType;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/26
 * @description
 */
@Component
public class ScoreCalculationConsumer {

    private static final String HOTLISTKEY="hotList";
    private static final String SCOREKEY="scoreKey";
    private static final String TIMEKEY="timeKey";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = "scoreCalculationQueue")
    public void scoreCalculation(@NotNull List<Object> data){
        MessageType messageType =(MessageType) data.get(0);
        String discussId =(String) data.get(1);

        //从redis中取值
        Object o = stringRedisTemplate.opsForHash().get(SCOREKEY, discussId);
        Object o1 = stringRedisTemplate.opsForHash().get(TIMEKEY, discussId);
        double oldScore = o==null? 1.5 : Double.parseDouble( (String) o);
        long timestamp = o1==null? System.currentTimeMillis() : Long.parseLong((String) o1);

        //计算新分数
        Double score = this.calculate(messageType, timestamp, oldScore);
        stringRedisTemplate.opsForZSet().add(HOTLISTKEY,discussId,score);

        //将新分数和相关信息写入redis中
        long currentTime=System.currentTimeMillis();
        stringRedisTemplate.opsForHash().put(TIMEKEY,discussId,String.valueOf(currentTime));
        stringRedisTemplate.opsForHash().put(SCOREKEY,discussId,String.valueOf(score));
    }

    @NotNull
    private Double calculate(MessageType messageType, long timestamp, double oldScore){
        double baseScore=1;
        long hours=(System.currentTimeMillis()-timestamp)/(3600*1000);
        if(hours<=24){
            oldScore*=0.8;
        }else if(hours<=120){
            oldScore*=0.5;
        }else oldScore*=0.2;

        if(messageType==MessageType.READ){
            baseScore+=baseScore*0.1;
        }else if(messageType==MessageType.LIKE){
            baseScore+=baseScore*0.2;
        }else if(messageType==MessageType.COMMENT){
            baseScore+=baseScore*0.5;
        }else if(messageType==MessageType.COLLECTION){
            baseScore+=baseScore*0.7;
        }
        return baseScore+oldScore;
    }

}































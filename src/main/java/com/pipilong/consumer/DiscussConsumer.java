package com.pipilong.consumer;

import com.pipilong.enums.MessageType;
import com.pipilong.mapper.DiscussMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author pipilong
 * @createTime 2023/2/17
 * @description
 */
@Component
public class DiscussConsumer {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DiscussMapper discussMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "readQueue")
    public void read(List<String> data){
        String authorId = data.get(0);
        String discussId = data.get(1);
        String key1 = "userReadCount:";
        String key2 = "discussReadCount:";
        this.authorsContributionChange(key1,true,authorId);
        this.authorsContributionChange(key2,true,discussId);
        rabbitTemplate.convertAndSend("scoreCalculationExchange","scoreCalculation", Arrays.asList(MessageType.READ,discussId));
    }

    @RabbitListener(queues = "likeQueue")
    public void like(List<Object> data){
        Boolean dianZan = (Boolean) data.get(0);
        String discussID = (String) data.get(1);
        String userId = (String) data.get(2);
        String authorId = (String) data.get(3);
        String discussUrl = (String) data.get(4);
        String username = (String) data.get(5);
        String discussTheme = (String) data.get(6);

        Object res = discussMapper.selectIsLike(discussID, userId);
        if(res == null){
            discussMapper.insertLike(discussID, userId);
        } else {
            discussMapper.updateLike(dianZan, discussID, userId);
        }

        String key = "userLikeCount:";
        this.authorsContributionChange(key,dianZan,authorId);
        String key2 = "discussLikeCount:";
        this.authorsContributionChange(key2,true,discussID);

        HashOperations<String, Object, Object> ops = stringRedisTemplate.opsForHash();
        String key1 = "likeMessage:"+discussID;

        if(!dianZan){
            if(Objects.equals(ops.get(key1, "count"), 1)) stringRedisTemplate.delete(key1);
            else ops.increment(key1,"count",-1);
        }else {
            ops.putIfAbsent(key1,"date",new SimpleDateFormat("yyyy-MM-dd HH-mm").format(new Date()));
            ops.put(key1,"authorId",authorId);
            ops.put(key1,"eventSourceUrl",discussUrl);
            ops.put(key1,"username",username);
            ops.put(key1,"discussTheme",discussTheme);
            ops.putIfAbsent(key1,"count","0");
            ops.increment(key1,"count",1);
        }

        rabbitTemplate.convertAndSend("scoreCalculationExchange","scoreCalculation", Arrays.asList(MessageType.LIKE,discussID));
    }

    @RabbitListener(queues = "collectionQueue")
    public void collection(List<Object> data){
        Boolean collection = (Boolean) data.get(0);
        String discussId = (String) data.get(1);
        String userId = (String) data.get(2);
        String authorId = (String) data.get(3);

        Object res = discussMapper.selectIsCollection(discussId,userId);
        if(res==null){
            discussMapper.insertCollection(discussId,userId);
        } else {
            discussMapper.updateCollection(collection,discussId,userId);
        }

        String key = "userCollectionCount:";
        this.authorsContributionChange(key,collection,authorId);
        String key2 = "discussCollectionCount:";
        this.authorsContributionChange(key2,true,discussId);

        rabbitTemplate.convertAndSend("scoreCalculationExchange","scoreCalculation", Arrays.asList(MessageType.COLLECTION,discussId));
    }

    private void authorsContributionChange(String key, @NotNull Boolean change, String id){

        stringRedisTemplate.opsForHash().putIfAbsent(key,id,"1073741823");
        if(change){
            stringRedisTemplate.opsForHash().increment(key,id,1);
        }else{
            stringRedisTemplate.opsForHash().increment(key,id,-1);
        }

    }

}

package com.pipilong.service.Impl;

import com.pipilong.enums.MessageType;
import com.pipilong.mapper.DiscussMapper;
import com.pipilong.mapper.EventMapper;
import com.pipilong.pojo.Comment;
import com.pipilong.pojo.Discuss;
import com.pipilong.pojo.PersonMessage;
import com.pipilong.service.DiscussService;
import com.pipilong.utils.CodeGenerator;
import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pipilong
 * @createTime 2023/2/7
 * @description
 */
@Service
@Slf4j
public class DiscussServiceImpl implements DiscussService {

    @Autowired
    private DiscussMapper discussMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private PersonMessage personMessage;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Discuss getDiscuss(String discussId) {
        Discuss discuss = discussMapper.getDiscuss(discussId);
        String avatarUrl="https://cdn.pipilong.com.cn/UserAvatar/"+discuss.getUserId()+".jpg";
        discuss.setUserAvatarUrl(avatarUrl);
        return discuss;
    }

    @Override
    public boolean selectIsCollection(String discussId,String userId) {
        Boolean res = discussMapper.selectIsCollection(discussId, userId);
        if(res==null) return false;
        return res;
    }

    @Override
    public boolean selectIsLike(String discussId,String userId) {
        Boolean res = discussMapper.selectIsLike(discussId,userId);
        if(res==null) return false;
        return res;
    }

    @Override
    public void dianZan(Boolean dianZan, String discussID, String userId,String authorId,String discussUrl,String username,String discussTheme) {

        rabbitTemplate.convertAndSend("discussExchange","like", Arrays.asList(dianZan,discussID,userId,authorId,discussUrl,username,discussTheme));

    }

    @Override
    public void collection(Boolean collection, String discussId, String userId, String authorId) {

        rabbitTemplate.convertAndSend("discussExchange","collection", Arrays.asList(collection,discussId,userId,authorId));
    }

    @Override
    @Transactional
    public void submitReply(Comment comment) {

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        comment.setDate(date);
        String userAvatarUrl="https://cdn.pipilong.com.cn/UserAvatar/"+comment.getUserId()+".jpg";
        comment.setUserAvatarUrl(userAvatarUrl);
        comment.setReplyId(codeGenerator.getCode(8));
        discussMapper.submitReply(comment);

        personMessage.setMessageId(codeGenerator.getCode(8));
        personMessage.setMessageType(MessageType.COMMENT);
        personMessage.setText("<span style=\"color:#5b91d9\">"+comment.getUsername()+"</span>"+" 评论了你发布的讨论 " +"<span style=\"color:#5b91d9\">"+comment.getDiscussTheme()+"</span>");
        personMessage.setIsRead(false);
        personMessage.setEventSourceId(comment.getDiscussId());
        personMessage.setUserId(comment.getAuthorId());
        personMessage.setEventSourceUrl(comment.getDiscussUrl());
        personMessage.setDate(date);
        eventMapper.insertPersonMessage(personMessage);

        rabbitTemplate.convertAndSend("scoreCalculationExchange","scoreCalculation", Arrays.asList(MessageType.COMMENT,comment.getDiscussId()));

    }

    @Override
    public List<Comment> getComment(String discussId) {

        return discussMapper.getComment(discussId);

    }

    @Override
    public void readDiscuss(String authorId, String discussId) {

        rabbitTemplate.convertAndSend("discussExchange","read", Arrays.asList(authorId,discussId));
    }


    private void authorsContributionChange(String key, @NotNull Boolean change, String authorId){

        stringRedisTemplate.opsForHash().putIfAbsent(key,authorId,"1073741823");
        if(change){
            stringRedisTemplate.opsForHash().increment(key,authorId,1);
        }else{
            stringRedisTemplate.opsForHash().increment(key,authorId,-1);
        }

    }

}






































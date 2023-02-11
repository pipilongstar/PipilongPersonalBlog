package com.pipilong.service.Impl;

import com.pipilong.enums.MessageType;
import com.pipilong.mapper.DiscussMapper;
import com.pipilong.mapper.EventMapper;
import com.pipilong.pojo.Comment;
import com.pipilong.pojo.Discuss;
import com.pipilong.pojo.PersonMessage;
import com.pipilong.service.DiscussService;
import com.pipilong.utils.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

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

        Object res = discussMapper.selectIsLike(discussID, userId);
        if(res==null){
            discussMapper.insertLike(discussID, userId);
        } else {
            discussMapper.updateLike(dianZan, discussID, userId);
        }

        String key = "userLikeCount:";
        this.authorsContributionChange(key,dianZan,authorId);
        HashOperations<String, Object, Object> ops = stringRedisTemplate.opsForHash();
        String key1 = "likeMessage:"+discussID;

        if(!dianZan){
            if(Objects.equals(ops.get(key1, "count"), "1")) stringRedisTemplate.delete(key1);
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

    }

    @Override
    public void collection(Boolean collection, String discussId, String userId, String authorId) {

        Object res = discussMapper.selectIsCollection(discussId,userId);
        if(res==null){
            discussMapper.insertCollection(discussId,userId);
        } else {
            discussMapper.updateCollection(collection,discussId,userId);
        }

        String key = "userCollectionCount:";
        this.authorsContributionChange(key,collection,authorId);

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
        personMessage.setText("<span style=\"color = #5b91d9\">"+comment.getUsername()+"</span>"+" 评论了你发布的讨论 " +"<span style=\"color = #5b91d9\">"+comment.getDiscussTheme()+"</span>");
        personMessage.setIsRead(false);
        personMessage.setEventSourceId(comment.getDiscussId());
        personMessage.setUserId(comment.getAuthorId());
        personMessage.setEventSourceUrl(comment.getDiscussUrl());
        personMessage.setDate(date);
        eventMapper.insertPersonMessage(personMessage);

    }

    @Override
    public List<Comment> getComment(String discussId) {

        return discussMapper.getComment(discussId);

    }

    @Override
    public void readDiscuss(String authorId) {

        String key = "userReadCount:";
        this.authorsContributionChange(key,true,authorId);

    }


    private void authorsContributionChange(String key, @NotNull Boolean change, String authorId){

        stringRedisTemplate.opsForHash().putIfAbsent(key,authorId,"0");
        if(change){
            stringRedisTemplate.opsForHash().increment(key,authorId,1);
        }else{
            stringRedisTemplate.opsForHash().increment(key,authorId,-1);
        }

    }

}






































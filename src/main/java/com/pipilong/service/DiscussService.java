package com.pipilong.service;

import com.pipilong.pojo.Comment;
import com.pipilong.pojo.Discuss;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/7
 * @description
 */
@Service
public interface DiscussService {

    /**
     * 根据id获取discuss信息
     * @param discussId 讨论id
     * @return Discuss信息
     */
    Discuss getDiscuss(String discussId);


    /**
     * 查询该用户是否已经收藏
     * @return ture or false
     */
    boolean selectIsCollection(String discussId,String userId);

    /**
     * 查询该用户是否已经点赞
     * @return ture or false
     */
    boolean selectIsLike(String discussId,String userId);


    /**
     * 用户给讨论点赞
     * @param dianzan 是点赞还是取消
     * @param discussID 讨论id
     */
    void dianZan(Boolean dianzan,String discussID,String userId,String authorId,String discussUrl,String username,String discussTheme);

    /**
     * 用户在讨论页点击收藏
     * @param collection 收藏还是取消
     * @param discussId 讨论id
     * @param userId 用户id
     * @param authorId 作者id
     */
    void collection(Boolean collection,String discussId,String userId,String authorId);

    /**
     * 提交评论
     * @param comment 评论的信息
     */
    void submitReply(Comment comment);

    /**
     * 获取评论列表
     * @param discussId 讨论id
     * @return 评论列表
     */
    List<Comment> getComment(String discussId);

    /**
     * 讨论被阅读
     * @param authorId 作者id
     */
    void readDiscuss(String authorId);
}

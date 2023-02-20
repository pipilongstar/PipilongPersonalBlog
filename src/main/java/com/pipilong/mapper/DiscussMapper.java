package com.pipilong.mapper;

import com.pipilong.pojo.Comment;
import com.pipilong.pojo.Discuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/4
 * @description
 */
@Mapper
public interface DiscussMapper{

    /**
     * 提交用户讨论
     * @param discuss 用户提交的讨论
     */
    void submitDiscuss(@Param("discuss")Discuss discuss);

    /**
     * 根据讨论id，查询讨论信息
     * @param discussId 讨论id
     * @return 讨论信息
     */
    Discuss getDiscuss(@Param("discussId") String discussId);

    /**
     * 查询是否收藏该讨论
     * @param discussId 讨论id
     * @param userId 用户id
     * @return is_able
     */
    Boolean selectIsCollection(@Param("discussId") String discussId,@Param("userId") String userId);

    /**
     * 查询是否点赞该讨论
     * @param discussId 讨论id
     * @param userId 用户id
     * @return is_able
     */
    Boolean selectIsLike(@Param("discussId") String discussId,@Param("userId") String userId);

    /**
     * 点赞
     * @param discussId 讨论id
     * @param userId 用户id
     */
    void updateLike(@Param("dianZan") Boolean dianZan,@Param("discussId") String discussId, @Param("userId") String userId);

    /**
     * 创建点赞记录
     * @param discussId 讨论id
     * @param userId 用户id
     */
    void insertLike(@Param("discussId") String discussId, @Param("userId") String userId);

    /**
     * 收藏
     * @param discussId 讨论id
     * @param userId 用户id
     */
    void updateCollection(@Param("dianZan") Boolean dianZan,@Param("discussId") String discussId, @Param("userId") String userId);

    /**
     * 创建收藏记录
     * @param discussId 讨论id
     * @param userId 用户id
     */
    void insertCollection(@Param("discussId") String discussId, @Param("userId") String userId);


    /**
     * 提交评论
     * @param comment 评论信息
     */
    void submitReply(@Param("comment")Comment comment);

    /**
     * 获取评论列表
     * @param discussId 评论id
     * @return 评论列表
     */
    List<Comment> getComment(@Param("discussId") String discussId);

    /**
     * 更新讨论的点赞数
     * @param discussId 用户id
     * @param count 更新数
     */
    void updateDiscussLikeCount(@Param("discussId") String discussId,@Param("count") String count);

    /**
     * 更新讨论的收藏数
     * @param discussId 用户id
     * @param count 更新数
     */
    void updateDiscussCollectionCount(@Param("discussId") String discussId,@Param("count") String count);

    /**
     * 更新讨论的阅读数
     * @param discussId 用户id
     * @param count 更新数
     */
    void updateDiscussReadCount(@Param("discussId") String discussId,@Param("count") String count);
}

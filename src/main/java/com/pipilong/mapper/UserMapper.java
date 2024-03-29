package com.pipilong.mapper;

import com.pipilong.pojo.ChatRoom;
import com.pipilong.pojo.Discuss;
import com.pipilong.pojo.Follow;
import com.pipilong.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Mapper
public interface UserMapper {

    /**
     * 把用户相关信息注册到account_security表中
     * @param userId 用户id
     * @param email 电子邮箱
     * @param telephone 手机号
     */
    void registerUserToSecurity(@Param("userId") String userId, @Param("email") String email,@Param("telephone") String telephone,@Param("githubId") String githubId,@Param("qqId") String qqId,@Param("giteeId") String giteeId);

    /**
     * 把用户相关信息注册到user_data表中
     * @param userId 用户id
     * @param username 用户名
     */
    void registerUserToData(@Param("userId") String userId,@Param("username") String username);

    /**
     * 根据手机号查找用户id
     * @param telephone 用户手机号
     * @return userId
     */
    String getUserIdByTelephone(@Param("telephone") String telephone);

    /**
     * 根据手机号或者电子邮件查找密码
     * @param identifier 手机号或者电子邮件
     * @return 用户信息
     */
    User getPasswordByIdentifier(@Param("identifier") String identifier);

    /**
     * 修改个人简介信息
     * @param user 用户信息
     * @return true or false
     */
    void modifyProfile(@Param("user") User user);

    /**
     * 根据用户id查找手机号
     * @param userId 用户id
     * @return 手机号
     */
    String selectTelephoneByUserId(@Param("userid") String userId);

    /**
     * 修改手机号
     * @param telephone 新手机号
     */
    void modifyTelephone(@Param("telephone") String telephone,@Param("userid") String userId);

    /**
     * 根据用户id找邮箱
     * @param userId 用户id
     */
    String selectEmailByUserIdAfter(@Param("userid") String userId);

    /**
     * 修改电子邮件
     * @param email 新电子邮件地址
     * @param userId 用户id
     */
    void modifyEmail(@Param("email") String email,@Param("userid") String userId);

    /**
     * 根据用户id查找密码
     * @param userId 用户id
     * @return 加密后密码
     */
    String selectPassword(@Param("userid") String userId);

    /**
     * 修改密码
     * @param newPassword 新密码
     * @param userId 用户id
     */
    void modifyPassword(@Param("newpassword") String newPassword,@Param("userid") String userId);

    /**
     * 设置密码
     * @param newPassword 新密码
     * @param userId 用户id
     */
    void setPassword(@Param("newpassword") String newPassword,@Param("userid") String userId);

    /**
     * 根据用户id，获取用户信息
     * @param userId 用户id
     * @return User用户信息
     */
    User getProfile(@Param("userid") String userId);

    /**
     * 根据用户id，获取用户手机号
     * @param userId 用户id
     * @return 用户手机号
     */
    String getTelephoneByUserId(@Param("userid") String userId);

    /**
     * 根据用户id，获取用户电子邮件
     * @param userId 用户id
     * @return 用户电子邮件
     */
    String getEmailByUserID(@Param("userid") String userId);

    /**
     * 更新用户的点赞数
     * @param userId 用户id
     * @param count 数量
     */
    void updateUserLikeCount(@Param("userid") String userId,@Param("count") String count);

    /**
     * 更新用户的收藏数
     * @param userId 用户id
     * @param count 数量
     */
    void updateUserCollectionCount(@Param("userid") String userId,@Param("count") String count);

    /**
     * 更新用户的阅读数
     * @param userId 用户id
     * @param count 数量
     */
    void updateUserReadCount(@Param("userid") String userId,@Param("count") String count);

    /**
     * 查询用户全部id
     * @return 用户的全部id
     */
    List<String> selectUserId();

    /**
     * 根据用户id查用户名
     * @param userId 用户id
     * @return 用户名
     */
    String getUserNameByUserId (@Param("userid") String userId);

    /**
     * 获取用户动态信息
     * @param userId 用户id
     * @return 动态信息
     */
    List<Discuss> getDynamic(@Param("userid") String userId);

    /**
     * 获取收藏信息
     * @param userId 用户id
     * @return 收藏的信息
     */
    List<Discuss> getCollection(@Param("userid") String userId);

    /**
     * 关注好友
     * @param userId 用户id
     * @param friendId 好友id
     */
    void follow(@Param("userid") String userId,@Param("friendid") String friendId);

    /**
     * 获取粉丝列表
     * @param userId 用户id
     * @return 粉丝列表
     */
    List<Follow> getFollowed(@Param("userid") String userId);

    /**
     * 获取关注列表
     * @param userId 用户id
     * @return 好友列表
     */
    List<Follow> getFollow(@Param("userid") String userId);

    /**
     * 创建聊天室
     * @param chatRoom 聊天室信息
     */
    void createChatRoom(@Param("room")ChatRoom chatRoom);

}















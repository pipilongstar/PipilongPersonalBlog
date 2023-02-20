package com.pipilong.service;

import com.pipilong.exception.ModifyException;
import com.pipilong.pojo.Discuss;
import com.pipilong.pojo.Follow;
import com.pipilong.pojo.User;

import org.springframework.stereotype.Service;
import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Service
public interface UserService {

    /**
     * 注册用户
     * @param user 用户信息
     * @param sessionId 用户sessionId
     * @return 返回用户id
     */
    String register(User user, String sessionId) throws Exception;

    /**
     * 用户使用验证码登录
     * @param telephone 用户手机号
     * @param sessionId 用户sessionId
     * @return userId
     */
    String codeLogin(String telephone, String code, String sessionId) throws LoginException;

    /**
     * 根据手机号或电子邮箱登录
     * @param identifier 手机号或者电子邮箱
     * @param sessionId 用户session
     * @return userid
     */
    String passwordLogin(String identifier, String password, String sessionId) throws LoginException;

    /**
     * 修改用户个人资料
     * @param user 用户信息
     * @throws ModifyException 修改失败
     */
    void modifyProfile(User user) throws ModifyException;

    /**
     * 修改手机号
     * @param telephone 新手机号
     * @throws ModifyException 修改失败
     */
    void modifyTelephone(String telephone,String userId) throws ModifyException;

    /**
     * 修改电子邮箱
     * @param email 电子邮箱
     * @param userId 用户id
     * @throws ModifyException 修改失败异常
     */
    void modifyEmail(String email,String userId) throws ModifyException;

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param userId 用户id
     */
    void modifyPassword(String oldPassword,String newPassword,String userId) throws ModifyException;

    /**
     * 获取用户信息
     * @param sessionId 用户session
     * @return User,用户信息
     */
    User getProfile(String sessionId) throws LoginException;


    /**
     * 获取动态信息
     * @param userId 用户id
     * @return 用户发布的动态信息
     */
    List<Discuss> getDynamic(String userId);

    /**
     * 获取收藏信息
     * @param userId 用户id
     * @return 收藏的信息
     */
    List<Discuss> getCollection(String userId);

    /**
     * 关注好友
     * @param userId 用户id
     * @param friendId 好友id
     */
    void follow(String userId,String friendId);

    /**
     * 获取粉丝信息
     * @param userId 用户id
     * @return 粉丝列表
     */
    List<Follow> getFollowed(String userId);

    /**
     * 获取关注信息
     * @param userId 用户id
     * @return 关注列表
     */
    List<Follow> getFollow(String userId);

    /**
     * 创建聊天室
     */
    void createChatRoom(String userId,String friendId,String username);

    /**
     * 设置密码
     * @param newPassword 新密码
     */
    void settingPassword(String newPassword,String userId);
}
























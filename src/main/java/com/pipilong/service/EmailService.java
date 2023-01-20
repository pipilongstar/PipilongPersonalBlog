package com.pipilong.service;

import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Service;

/**
 * @author pipilong
 * @createTime 2023/1/20
 * @description
 */
@Service
public interface EmailService {

    /**
     * 发送电子邮件
     * @param email 电子邮件地址
     * @param message 发送信息
     */
    void sendEmail(String email, String message, String emailHead) throws EmailException;

    /**
     * 发送关于验证码的信息
     */
    void sendVerificationCode(String emailAddress, String session) throws EmailException;

    /**
     * 根据用户id找到邮箱，然后发验证码
     * @param userId 用户id
     * @param session 用户session
     */
    void sendVerificationCodeByUserId(String userId, String session) throws EmailException;

}

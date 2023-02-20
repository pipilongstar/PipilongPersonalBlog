package com.pipilong.service;

import org.springframework.stereotype.Service;

import javax.mail.SendFailedException;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description 短信服务接口
 */
@Service
public interface SmsService {

    /**
     * 对验证码进行处理，根据sessionId获取响应的验证码
     * @param telephone 手机号
     * @param sessionId 用户的session
     */
    void verificationCodeProcessing(String telephone, String sessionId) throws SendFailedException;

    /**
     * 发送短信的方法
     * @param telephone 手机号
     * @param message 发送短信的内容
     */
    void sendSMS(String telephone, String message,String textTemplateId);

    /**
     * 根据用户id找到手机号，并发送短信
     * @param userId 用户id
     */
    void sendSmsByUserId(String userId,String sessionId) throws SendFailedException;

}





















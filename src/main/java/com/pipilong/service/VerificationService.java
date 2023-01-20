package com.pipilong.service;

import org.springframework.stereotype.Service;

/**
 * @author pipilong
 * @createTime 2023/1/20
 * @description
 */
@Service
public interface VerificationService {


    /**
     * 验证验证码接口
     * @param code 验证码
     * @param sessionId 用户session
     * @return true or false
     */
    boolean verificationCode(String code,String sessionId);


}

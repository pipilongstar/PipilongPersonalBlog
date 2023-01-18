package com.pipilong.service;

import org.springframework.stereotype.Service;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Service
public interface GeneralOperation {

    void sendVerificationCode(String telephone, String sessionId);

}

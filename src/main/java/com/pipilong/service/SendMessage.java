package com.pipilong.service;

import org.springframework.stereotype.Service;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@Service
public interface SendMessage {

    void sendSMS(String telephone,String message);

    void sendEmail(String emailAddress,String message);

}

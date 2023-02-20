package com.pipilong.consumer;

import com.pipilong.annotation.ErrorLog;
import com.pipilong.pojo.User;
import com.pipilong.service.SmsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/2/17
 * @description
 */
@Component
public class SmsSendConsumer {

    @Autowired
    private SmsService smsService;

    @ErrorLog
    @RabbitListener(queues = "smsQueue")
    public void sendSms(User user){

        smsService.sendSMS(user.getTelephone(),user.getUserName(),"1698121");
    }

}
























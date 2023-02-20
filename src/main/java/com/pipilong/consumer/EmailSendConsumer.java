package com.pipilong.consumer;

import com.pipilong.annotation.ErrorLog;
import com.pipilong.pojo.User;
import com.pipilong.service.EmailService;
import org.apache.commons.mail.EmailException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/2/17
 * @description
 */
@Component
public class EmailSendConsumer {

    @Autowired
    private EmailService emailService;

    @ErrorLog
    @RabbitListener(queues = "emailQueue")
    public void sendEmail(User user) throws EmailException {

        String msg="<html>"+user.getUserName()+"：恭喜注册成功！不要因为没有掌声，就放弃自己所热爱的事情！加油，我们一起努力！" +
                "<img src=\"https://cdn.pipilong.com.cn/images/register.png\"></html>";
        emailService.sendEmail(user.getEmail(),msg,"恭喜加入皮皮龙博客！");
    }


}

























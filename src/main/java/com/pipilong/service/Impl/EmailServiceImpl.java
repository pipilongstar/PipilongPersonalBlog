package com.pipilong.service.Impl;

import com.pipilong.annotation.ErrorLog;
import com.pipilong.mapper.UserMapper;
import com.pipilong.service.EmailService;
import com.pipilong.utils.CodeGenerator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author pipilong
 * @createTime 2023/1/20
 * @description
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private CodeGenerator codeGenerator;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @ErrorLog
    @Override
    public void sendEmail(String emailAddress, String message, String emailHead) throws EmailException {
        HtmlEmail email = new HtmlEmail();//不用更改
        email.setHostName("smtp.pipilong.com.cn");// 发件者的邮箱  需要修改，126邮箱为smtp.126.com,163邮箱为smtp.163.com,QQ为smtp.qq.com
        email.setCharset("UTF-8");
        email.addTo(emailAddress);// 收件地址 收件人邮箱

        email.setFrom("official@pipilong.com.cn", "皮皮龙科技");//此处填邮箱地址和用户名,用户名可以任意填写
        email.setAuthentication("official@pipilong.com.cn","tHo7C8jao8kEDdd3");
//            email.setAuthentication("2534158602@qq.com", "tazojgkxgzrhdijj");//此处填写邮箱地址和客户端授权码
        email.setSubject(emailHead);//此处填写邮件名，邮件名可任意填写
        email.setHtmlMsg(message);//此处填写邮件内容
        email.send();
    }

    @ErrorLog
    @Override
    public void sendVerificationCode(String emailAddress, String sessionId) throws EmailException {

        String key="verificationCode:"+sessionId;
        String code = stringRedisTemplate.opsForValue().get(key);
        if(code == null) {
            code = codeGenerator.getCode(6);
        }
        stringRedisTemplate.opsForValue().set(key,code,300, TimeUnit.SECONDS);
        String msg="您正在进行邮箱身份验证，验证码为："+code+"，验证码5分钟内有效，若非本人操作，请勿泄露。";
        this.sendEmail(emailAddress,msg,"电子邮箱验证");

    }

    @ErrorLog
    @Override
    public void sendVerificationCodeByUserId(String userId, String sessionId) throws EmailException {

        String emailAddress = userMapper.selectEmailByUserIdAfter(userId);
        this.sendVerificationCode(emailAddress,sessionId);

    }


}
























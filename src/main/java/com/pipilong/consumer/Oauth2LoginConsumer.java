package com.pipilong.consumer;

import com.pipilong.mapper.UserMapper;
import com.pipilong.service.UploadService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * @author pipilong
 * @createTime 2023/2/27
 * @description
 */
@Component
public class Oauth2LoginConsumer {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UserMapper userMapper;

    @RabbitListener(queues = "githubQueue")
    public void github(Map<String,String> userData) {
        String githubId=userData.get("id");
        String username=userData.get("name");
        String avatarUrl=userData.get("avatar_url");
        String githubUrl=userData.get("html_url");
        String userId=userData.get("userId");
        userMapper.registerUserToSecurity(userId,"null","null",githubId,"null","null");
        userMapper.registerUserToData(userId,username);

        try {
            URL url = new URL(avatarUrl);
            InputStream is = url.openStream();
            uploadService.upload(is,userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "giteeQueue")
    public void gitee(Map<String,String> userData){

//        System.out.println(userData);
    }

}

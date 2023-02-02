package com.pipilong.service.Impl;

import com.pipilong.annotation.ErrorLog;
import com.pipilong.service.UploadService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author pipilong
 * @createTime 2023/2/1
 * @description
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private COSClient cosClient;

    @ErrorLog
    @Override
    public void uploadToCos(InputStream is, String sessionId) throws IOException {

        String key="user:"+sessionId;
        String userId = stringRedisTemplate.opsForValue().get(key);

        String bucketName = "pipilong-blog-1313596756";
        String cosKey = "UserAvatar/"+ userId+".jpg";
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(is.available());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, cosKey, is,metadata);
        cosClient.putObject(putObjectRequest);

    }
}


































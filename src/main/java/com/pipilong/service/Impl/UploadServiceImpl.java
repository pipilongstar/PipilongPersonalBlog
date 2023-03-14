package com.pipilong.service.Impl;

import com.alibaba.fastjson.JSON;
import com.pipilong.annotation.ErrorLog;
import com.pipilong.mapper.DiscussMapper;
import com.pipilong.pojo.Discuss;
import com.pipilong.service.SubmitElasticSearchService;
import com.pipilong.service.UploadService;
import com.pipilong.utils.CodeGenerator;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author pipilong
 * @createTime 2023/2/1
 * @description
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    private static final String HOTLISTKEY="hotList";
    private static final String SCOREKEY="scoreKey";
    private static final String TIMEKEY="timeKey";
    @Autowired
    private CodeGenerator codeGenerator;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private COSClient cosClient;

    @Autowired
    private DiscussMapper discussMapper;

    @Autowired
    private SubmitElasticSearchService submitElasticSearchService;

    @ErrorLog
    @Transactional
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

    @Override
    public void uploadImage(InputStream is,String filename) throws IOException {

        String bucketName = "pipilong-blog-1313596756";
        String cosKey="images/"+filename;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(is.available());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, cosKey, is,metadata);
        cosClient.putObject(putObjectRequest);

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED)
    @Override
    public void uploadDiscuss(@NotNull Discuss discuss) throws IOException {

        discuss.setSubmitDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        discuss.setDiscussId(Long.parseLong(codeGenerator.getCode(8)));
        discussMapper.submitDiscuss(discuss);
        submitElasticSearchService.submitDiscuss(discuss);
        stringRedisTemplate.opsForHash().putIfAbsent(SCOREKEY,String.valueOf(discuss.getDiscussId()),String.valueOf(1.5));
        stringRedisTemplate.opsForHash().putIfAbsent(TIMEKEY,String.valueOf(discuss.getDiscussId()),String.valueOf(System.currentTimeMillis()));
        stringRedisTemplate.opsForZSet().addIfAbsent(HOTLISTKEY,String.valueOf(discuss.getDiscussId()),1.5);

    }
}


































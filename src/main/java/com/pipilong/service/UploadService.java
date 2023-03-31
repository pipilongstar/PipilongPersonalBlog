package com.pipilong.service;

import com.pipilong.pojo.Discuss;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Service
public interface UploadService {

    /**
     * 上传文件到cos
     * @param is 文件输入流
     * @param sessionId 用户sessioin
     */
    void uploadToCos(InputStream is,String sessionId) throws IOException;

    /**
     * 上传用户上传的图片
     * @param is 图片文件
     * @param filename 图片名
     */
    void uploadImage(InputStream is,String filename) throws IOException;

    /**
     * 上传用户发布的讨论
     * @param discuss 用户发布的讨论
     */
    void uploadDiscuss(Discuss discuss) throws IOException;

    /**
     * 上传
     * @param is 输入流
     * @param userId 用户id
     * @throws IOException io异常
     */
    public void upload(InputStream is,String userId) throws IOException;
}






















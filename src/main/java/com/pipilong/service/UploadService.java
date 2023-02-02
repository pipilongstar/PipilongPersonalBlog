package com.pipilong.service;

import org.springframework.stereotype.Service;

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

}

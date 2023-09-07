package com.pipilong.service;

/**
 * @author pipilong
 * @createTime 2023/8/7
 * @description
 */
public interface UtilService {

    String getToken(String sessionId);

    boolean verifyToken(String sessionId,String token);

}

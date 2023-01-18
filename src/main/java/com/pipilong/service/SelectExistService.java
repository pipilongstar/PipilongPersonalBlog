package com.pipilong.service;

import org.springframework.stereotype.Service;

/**
 * @author pipilong
 * @createTime 2023/1/19
 * @description
 */
@Service
public interface SelectExistService {

    /**
     * 查询数据库，判断手机是否存在
     * @param telephone 手机号
     * @return true or false
     */
    boolean telephone(String telephone);


}

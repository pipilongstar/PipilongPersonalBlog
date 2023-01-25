package com.pipilong.service.Impl;

import com.pipilong.mapper.SelectExistMapper;
import com.pipilong.service.SelectExistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pipilong
 * @createTime 2023/1/19
 * @description
 */
@Service
public class SelectExistServiceImpl implements SelectExistService {

    @Autowired
    private SelectExistMapper selectExistMapper;

    @Override
    public boolean telephone(String telephone) {
        return selectExistMapper.telephone(telephone);
    }

    @Override
    public boolean email(String email) {
        return selectExistMapper.email(email);
    }

    @Override
    public boolean password(String userId) {
        return selectExistMapper.password(userId) != null;
    }

}

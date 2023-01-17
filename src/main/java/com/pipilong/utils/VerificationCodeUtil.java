package com.pipilong.utils;

import java.util.Random;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description 获取验证码的工具
 */
public class VerificationCodeUtil {

    public static String getCode(){
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            int next = random.nextInt(10);
            code.append(next);
        }
        return code.toString();
    }

}

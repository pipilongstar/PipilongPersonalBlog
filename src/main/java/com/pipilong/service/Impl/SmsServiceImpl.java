package com.pipilong.service.Impl;

import com.pipilong.service.SmsService;
import com.pipilong.utils.CodeGenerator;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CodeGenerator codeGenerator;

    @Override
    public void verificationCodeProcessing(String telephone, String sessionId) {
        String key="verificationCode:"+sessionId;

        String code = stringRedisTemplate.opsForValue().get(key);
        if(code == null) {
            code = codeGenerator.getCode(6);
        }

        stringRedisTemplate.opsForValue().set(key,code,300, TimeUnit.SECONDS);
        sendSMS(telephone,code);
    }

    @Override
    public void sendSMS(String telephone, String message) {
        try {

            Credential cred = new Credential("AKIDUroZEk7OejgR5Ys13kIgvGZjhXDxYxBD", "vfFlYBiBTo5HRZgq2B4IWisz42tcHtX8");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();

            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou",clientProfile);

            SendSmsRequest req = new SendSmsRequest();

            String sdkAppId = "1400784898";
            req.setSmsSdkAppId(sdkAppId);

            String signName = "皮皮龙技术个人网";
            req.setSignName(signName);

            String templateId = "1667715";
            req.setTemplateId(templateId);

            String[] templateParamSet = {message};
            req.setTemplateParamSet(templateParamSet);

            String[] phoneNumberSet = {telephone};
            req.setPhoneNumberSet(phoneNumberSet);

            SendSmsResponse res = client.SendSms(req);

        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public boolean verificationCode(String code, String sessionId) {
        String key="verificationCode:"+sessionId;
        String saveCode = stringRedisTemplate.opsForValue().get(key);
        return saveCode != null && saveCode.equals(code);
    }

}

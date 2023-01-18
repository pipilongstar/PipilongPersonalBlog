package com.pipilong.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Slf4j
@Component
public class ShortMessageUtil1 {

    public void sendSMS(String telephone, String message){
        try {

            Credential cred = new Credential("secretId", "secretKey");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();

            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou",clientProfile);

            SendSmsRequest req = new SendSmsRequest();

            String sdkAppId = "sdkAppId";
            req.setSmsSdkAppId(sdkAppId);

            String signName = "皮皮龙技术个人网";
            req.setSignName(signName);

            String templateId = "templateId";
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

}

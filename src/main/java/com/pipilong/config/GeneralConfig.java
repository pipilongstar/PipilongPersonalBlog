package com.pipilong.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@Configuration
public class GeneralConfig {

    @Bean(name = "COSClient")
    public COSClient getCOSClient(){
        String secretId = "AKIDVoqlSu73kCPXX0EoHWVKwKCsGNLErESV";
        String secretKey = "2qPgqNlz2tXw72iBPN9i4A0av0oPwHQL";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);

        return new COSClient(cred, clientConfig);
    }


}

































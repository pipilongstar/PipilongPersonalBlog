package com.pipilong.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.concurrent.*;

/**
 * @author pipilong
 * @createTime 2023/1/17
 * @description
 */
@Configuration
public class GeneralConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        /*1、按错误的类型显示错误的网页*/
        /*错误类型为404，找不到网页的，默认显示404.html网页*/
        ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
        /*错误类型为500，表示服务器响应错误，默认显示500.html网页*/
//        ErrorPage e500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
        /*2、按具体某个异常显示错误的网页*/
        /*当某个异常即可以根据错误类型显示错误网页，由可以根据某个具体的异常来显示错误网页时，优先根据具体的某个异常显示错误的网页*/
        registry.addErrorPages(e404);
    }
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

    @Bean("taskExecutor")
    public ThreadPoolExecutor getThreadPoolExecutor(){
        return new ThreadPoolExecutor(
                5,
                10,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

}

































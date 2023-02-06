package com.cdtde.chongdetang.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description COS服务配置
 * @Author John
 * @email
 * @Date 2023/2/6 18:19
 * @Version 1
 */

@Configuration
public class CosConfig {

    @Value("${cos.secret-id}")
    private String secretId;

    @Value("${cos.secret-key}")
    private String secretKey;

    @Bean
    public COSClient cosClient() {
        COSCredentials credentials = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-beijing");
        ClientConfig config = new ClientConfig(region);
        return new COSClient(credentials, config);
    }
}

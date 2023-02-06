package com.cdtde.chongdetang.service.impl;

import com.cdtde.chongdetang.service.CosService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/6 18:44
 * @Version 1
 */
@Service
@Slf4j
public class CosServiceImpl implements CosService {

    @Value("${cos.bucket}")
    private String bucket;

    @Autowired
    private COSClient client;

    @Override
    public void upload(File file, String objectKey) throws CosClientException {
        PutObjectRequest request = new PutObjectRequest(bucket, objectKey, file);
        client.putObject(request);
    }
}

package com.cdtde.chongdetang.service.impl;

import com.cdtde.chongdetang.service.CosService;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.transfer.*;
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
    private TransferManager transferManager;

    @Override
    public void upload(File file, String objectKey) throws InterruptedException {
        PutObjectRequest request = new PutObjectRequest(bucket, objectKey, file);
        Upload upload = transferManager.upload(request);
        showProgress(upload);
        upload.waitForUploadResult();
    }

    @Override
    public void download(File file, String objectKey) throws InterruptedException {
        GetObjectRequest request = new GetObjectRequest(bucket, objectKey);
        Download download = transferManager.download(request, file);
        showProgress(download);
        download.waitForCompletion();
    }

    @SuppressWarnings("BusyWait")
    private void showProgress(Transfer transfer) {
        log.info("cos info: {}", transfer.getDescription());

        while (!transfer.isDone()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }

            TransferProgress progress = transfer.getProgress();
            long soFar = progress.getBytesTransferred();
            long total = progress.getTotalBytesToTransfer();
            double pct = progress.getPercentTransferred();
            String info = String.format("cos progress: [%d / %d] = %.02f%%\n", soFar, total, pct);
            log.info(info);
        }

        log.info("cos info: {}", transfer.getState());
    }
}

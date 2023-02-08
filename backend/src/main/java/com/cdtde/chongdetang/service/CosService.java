package com.cdtde.chongdetang.service;

import java.io.File;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/6 18:42
 * @Version 1
 */
public interface CosService {

    void upload(File file, String objectKey) throws InterruptedException;

    void download(File file, String objectKey) throws InterruptedException;
}

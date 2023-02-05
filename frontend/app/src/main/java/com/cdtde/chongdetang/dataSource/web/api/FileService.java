package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.ResponseResult;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/4 15:02
 * @Version 1
 */
public interface FileService {
    @POST("/resource/upload")
    @Multipart
    ResponseResult<Object> upload(MultipartBody.Part file);

    @GET("/resource/download")
    ResponseResult<File> download(@Url String url);
}

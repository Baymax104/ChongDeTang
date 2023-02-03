package com.cdtde.chongdetang.dataSource.web;

import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.repository.AppKey;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/2 2:35
 * @Version 1
 */
public class WebService {
    private Retrofit retrofit;

    private static WebService instance;

    public WebService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(s -> Log.i("web-log", s));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(AppKey.TEST_SERVER_BASE_URL)
                .build();
    }

    synchronized public static WebService getInstance() {
        if (instance == null) {
            instance = new WebService();
        }
        return instance;
    }

    public <T> T create(Class<T> cl) {
        return retrofit.create(cl);
    }
}
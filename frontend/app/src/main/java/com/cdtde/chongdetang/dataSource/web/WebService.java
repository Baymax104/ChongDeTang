package com.cdtde.chongdetang.dataSource.web;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.repository.AppKey;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
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

    private WebService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(s -> Log.i("cdt-web-log", s));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
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

    public static Consumer<Throwable> onError(String functionName, String eventKey) {
        return throwable1 -> {
            LogUtils.eTag("cdt-web-" + functionName, throwable1);
            LiveEventBus.get(eventKey, WebException.class)
                    .post(new WebException(false, throwable1.getMessage()));
        };
    }
}

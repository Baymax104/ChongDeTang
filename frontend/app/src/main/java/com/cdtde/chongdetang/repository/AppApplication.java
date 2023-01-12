package com.cdtde.chongdetang.repository;

import android.app.Application;

import com.cdtde.chongdetang.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 使用Application存储全局数据，在应用的整个生命周期有效
 *              调用AppUtil.getApp可以获取该对象，调用字段
 * @Author John
 * @email
 * @Date 2023/1/11 1:50
 * @Version 1
 */
public class AppApplication extends Application {
    private Map<String, Object> globalMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        globalMap.put("user", User.getInstance());
    }

    public Map<String, Object> getGlobalMap() {
        return globalMap;
    }
}

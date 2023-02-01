package com.cdtde.chongdetang.repository;

import android.app.Application;

import com.cdtde.chongdetang.entity.User;
import com.tencent.mmkv.MMKV;

/**
 * @Description 使用Application存储全局数据，在应用的整个生命周期有效
 *              利用AndroidUtilCode库，调用Util.getApp可以获取该对象，调用字段
 * @Author John
 * @email
 * @Date 2023/1/11 1:50
 * @Version 1
 */
public class AppApplication extends Application {
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
        MMKV mmkv = MMKV.defaultMMKV();
        if (!mmkv.contains("user")) {
            user = User.newInstance();
        } else {
            user = mmkv.decodeParcelable("user", User.class);
        }
    }

    public void setUser(User user) {
        this.user = user;
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.clearAll();
    }

    public User getUser() {
        return user;
    }
}

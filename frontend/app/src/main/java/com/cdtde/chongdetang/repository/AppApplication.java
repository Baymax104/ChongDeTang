package com.cdtde.chongdetang.repository;

import android.app.Application;

import com.cdtde.chongdetang.entity.User;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.XLog;
import com.tencent.mmkv.MMKV;

/**
 * 使用Application存储全局数据，在应用的整个生命周期有效.
 * @author John
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

        LogConfiguration configuration = new LogConfiguration.Builder()
                .tag("cdt-log")
                .enableBorder()
                .build();

        XLog.init(configuration);
    }

    public void setUser(User user) {
        this.user = user;
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode("user", user);
    }

    public void initUser() {
        user = User.newInstance();
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.clearAll();
    }

    public User getUser() {
        return user;
    }
}

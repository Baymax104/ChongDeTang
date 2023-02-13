package com.cdtde.chongdetang.repository;

import com.blankj.utilcode.util.Utils;
import com.cdtde.chongdetang.entity.User;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 22:59
 * @Version 1
 */
public class UserRepository {

    private static UserRepository userRepository;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public User getUser() {
        AppApplication app = (AppApplication) Utils.getApp();
        return app.getUser();
    }

    public void initUser() {
        AppApplication app = (AppApplication) Utils.getApp();
        app.initUser();
    }

    public void setUser(User user) {
        AppApplication app = (AppApplication) Utils.getApp();
        app.setUser(user);
    }
}

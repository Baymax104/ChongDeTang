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
    private User user;

    private static UserRepository userRepository;

    private UserRepository() {
        AppApplication app = (AppApplication) Utils.getApp();
        user = app.getUser();
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public User getUser() {
        return user;
    }

    public void initUser() {
        user = User.newInstance();
        AppApplication app = (AppApplication) Utils.getApp();
        app.setUser(user);
    }
}

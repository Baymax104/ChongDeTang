package com.cdtde.chongdetang.repository;

import com.blankj.utilcode.util.Utils;
import com.cdtde.chongdetang.entity.User;

import java.util.Map;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/11 1:20
 * @Version 1
 */
public class MyRepository {
    private User user;

    private static MyRepository repository;

    private MyRepository() {
        AppApplication app = (AppApplication) Utils.getApp();
        Map<String, Object> globalMap = app.getGlobalMap();
        user = (User) globalMap.get("user");
    }

    public static MyRepository getInstance() {
        if (repository == null) {
            repository = new MyRepository();
        }
        return repository;
    }

    public User getUser() {
        return user;
    }
}

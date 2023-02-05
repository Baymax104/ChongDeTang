package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/3 14:53
 * @Version 1
 */
public interface UserService {

    ResponseResult<User> login(String phone, String password);

    ResponseResult<Object> register(String phone, String password);

    ResponseResult<User> updateInfo(User user);

    ResponseResult<String> updatePassword(String oldPassword, String newPassword);

    ResponseResult<Object> updatePhone(String phone);
}

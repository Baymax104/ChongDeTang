package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;


public interface UserService {

    ResponseResult<User> login(String phone, String password);

    ResponseResult<Object> register(String phone, String password);

    ResponseResult<User> updateInfo(User user, String objectKey);

    ResponseResult<String> uploadPhoto(String base64);

    ResponseResult<String> updatePassword(String oldPassword, String newPassword);

    ResponseResult<Object> updatePhone(String phone);
}

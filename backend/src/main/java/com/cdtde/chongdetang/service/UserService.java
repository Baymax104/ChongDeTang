package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.pojo.UserCollect;

import java.util.List;


public interface UserService {

    ResponseResult<User> login(String phone, String password);

    ResponseResult<Object> register(String phone, String password);

    ResponseResult<User> updateInfo(User user, String objectKey);

    ResponseResult<String> updatePassword(String oldPassword, String newPassword);

    ResponseResult<Object> updatePhone(String phone);

    ResponseResult<List<UserCollect>> getUserCollect(String type);

    ResponseResult<Object> addUserCollect(UserCollect userCollect);

    ResponseResult<Object> removeUserCollect(UserCollect userCollect);

    ResponseResult<Object> setAdmin(String phone, String mode);

    ResponseResult<List<User>> getAllUser();

    ResponseResult<Object> addFeedback(String content);

    ResponseResult<Object> checkToken(String token);
}

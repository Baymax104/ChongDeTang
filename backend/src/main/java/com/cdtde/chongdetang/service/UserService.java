package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.*;

import java.util.List;


public interface UserService {

    Result<User> login(String phone, String password);

    Result<Object> register(String phone, String password);

    Result<User> updateInfo(User newUser);

    Result<String> updatePassword(String oldPassword, String newPassword);

    Result<Object> forgetPassword(String phone, String newPassword);

    Result<Object> updatePhone(String phone);

    Result<Object> setAdmin(String phone, String mode);

    Result<List<User>> getAllUser();

    Result<Object> addFeedback(String content);

    Result<Object> checkToken(String token);
}

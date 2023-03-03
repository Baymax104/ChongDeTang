package com.cdtde.chongdetang.controller;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.pojo.UserCollect;
import com.cdtde.chongdetang.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/3 14:48
 * @Version 1
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${password.key}")
    private String passwordKey;

    @Value("${password.iv}")
    private String passwordIv;

    @PostMapping("/login")
    public ResponseResult<User> login(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String aesPassword = map.get("password");
        byte[] key = passwordKey.getBytes(StandardCharsets.UTF_8);
        byte[] iv = passwordIv.getBytes(StandardCharsets.UTF_8);
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
        String password = aes.decryptStr(aesPassword);

        return userService.login(phone, password);
    }

    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String aesPassword = map.get("password");

        // 密码为AES加密传输，需要解密后重新加密
        byte[] key = passwordKey.getBytes(StandardCharsets.UTF_8);
        byte[] iv = passwordIv.getBytes(StandardCharsets.UTF_8);
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
        String password = aes.decryptStr(aesPassword);

        return userService.register(phone, password);
    }

    @PostMapping("/update/info")
    public ResponseResult<User> updateInfo(@RequestBody Map<String, Object> map) {
        // Object接收json对象会转换为Map(LinkedHashMap)，手动转换为User对象
        String userJson = new Gson().toJson(map.get("user"));
        User user = new Gson().fromJson(userJson, User.class);

        // user中的photo字段已经是新头像的URI路径，只需要将文件上传COS即可
        // 数据库中保存文件在COS中的ObjectKey
        String newPhoto = (String) map.get("newPhoto");
        return userService.updateInfo(user, newPhoto);
    }

    @PostMapping("/update/password")
    public ResponseResult<String> updatePassword(@RequestBody Map<String, String> map) {
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");

        byte[] key = passwordKey.getBytes(StandardCharsets.UTF_8);
        byte[] iv = passwordIv.getBytes(StandardCharsets.UTF_8);
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
        String oldDecrypt = aes.decryptStr(oldPassword);
        String newDecrypt = aes.decryptStr(newPassword);

        return userService.updatePassword(oldDecrypt, newDecrypt);
    }

    @PostMapping("/update/phone")
    public ResponseResult<Object> updatePhone(@RequestBody String phone) {
        return userService.updatePhone(phone);
    }

    @GetMapping("/collect/{type}")
    public ResponseResult<List<UserCollect>> getUserCollect(@PathVariable("type") String type) {
        return userService.getUserCollect(type);
    }

    @PostMapping("/collect")
    public ResponseResult<Object> addUserCollect(@RequestBody UserCollect userCollect) {
        return userService.addUserCollect(userCollect);
    }

    @PostMapping("/admin")
    public ResponseResult<Object> setAdmin(@RequestBody Map<String,String> map){
        String phone = map.get("phone");
        String mode = map.get("mode");
        return userService.setAdmin(phone,mode);
    }

    @GetMapping("/admin")
    public ResponseResult<List<User>> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/feedback")
    public ResponseResult<Object> addFeedback(@RequestBody String content) {
        return userService.addFeedback(content);
    }

}

package com.cdtde.chongdetang.controller;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
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
        String password = map.get("password");
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
        // user中的photo字段已经是新头像的URI路径，只需要将文件上传即可
        // 数据库中保存文件上传后得到的URL路径
        String newPhoto = (String) map.get("newPhoto");
        if (newPhoto != null) {
            // TODO upload
        }
        // Object接收json对象会转换为Map(LinkedHashMap)，手动转换为User对象
        String userJson = new Gson().toJson(map.get("user"));
        User user = new Gson().fromJson(userJson, User.class);
        return userService.updateInfo(user);
    }


}

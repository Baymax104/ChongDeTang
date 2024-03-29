package com.cdtde.chongdetang.controller;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.cdtde.chongdetang.pojo.*;
import com.cdtde.chongdetang.service.UserService;
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
    public Result<User> login(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String aesPassword = map.get("password");
        byte[] key = passwordKey.getBytes(StandardCharsets.UTF_8);
        byte[] iv = passwordIv.getBytes(StandardCharsets.UTF_8);
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
        String password = aes.decryptStr(aesPassword);

        return userService.login(phone, password);
    }

    @PostMapping("/register")
    public Result<Object> register(@RequestBody Map<String, String> map) {
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
    public Result<User> updateInfo(@RequestBody User newUser) {
        return userService.updateInfo(newUser);
    }

    @PostMapping("/update/password")
    public Result<String> updatePassword(@RequestBody Map<String, String> map) {
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");

        byte[] key = passwordKey.getBytes(StandardCharsets.UTF_8);
        byte[] iv = passwordIv.getBytes(StandardCharsets.UTF_8);
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
        String oldDecrypt = aes.decryptStr(oldPassword);
        String newDecrypt = aes.decryptStr(newPassword);

        return userService.updatePassword(oldDecrypt, newDecrypt);
    }

    @PostMapping("/forget")
    public Result<Object> updatePassword(@RequestParam("phone") String phone,
                                         @RequestParam("password") String password) {

        byte[] key = passwordKey.getBytes(StandardCharsets.UTF_8);
        byte[] iv = passwordIv.getBytes(StandardCharsets.UTF_8);
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
        String newDecrypt = aes.decryptStr(password);

        return userService.forgetPassword(phone, newDecrypt);
    }

    @PostMapping("/update/phone")
    public Result<Object> updatePhone(@RequestParam("phone") String phone) {
        return userService.updatePhone(phone);
    }


    @PostMapping("/admin")
    public Result<Object> setAdmin(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String mode = map.get("mode");
        return userService.setAdmin(phone, mode);
    }

    @GetMapping("/admin")
    public Result<List<User>> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/feedback")
    public Result<Object> addFeedback(@RequestBody String content) {
        content = content.substring(1, content.length() - 1);
        return userService.addFeedback(content);
    }

    @PostMapping("/checkToken")
    public Result<Object> checkToken(@RequestHeader("Authorization") String token) {
        return userService.checkToken(token);
    }


}

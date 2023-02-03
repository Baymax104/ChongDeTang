package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/3 14:48
 * @Version 1
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult<User> login(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String password = map.get("password");
        return userService.login(phone, password);
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String password = map.get("password");
        return userService.register(phone, password);
    }
}

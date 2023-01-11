package com.cdtde.chongdetang.controller.account;

import com.cdtde.chongdetang.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/api/user/account/token/")
    public Map<String,String> getToken(@RequestParam Map<String,String> map){
        String phone = map.get("phone");
        String password = map.get("password");
        return loginService.getToken(phone,password);
    }
}

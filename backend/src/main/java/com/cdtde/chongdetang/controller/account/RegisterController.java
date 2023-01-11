package com.cdtde.chongdetang.controller.account;

import com.cdtde.chongdetang.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @PostMapping("/api/user/account/register/")
    public Map<String,String> register(@RequestParam Map<String,String> map){
        String phone = map.get("phone");
        String password = map.get("password");
        String confirmedPassword = map.get("confirmedPassword");
//        String mail = map.get("mail");
//        String phone = map.get("phone");

        return registerService.register(phone,password,confirmedPassword);
    }
}

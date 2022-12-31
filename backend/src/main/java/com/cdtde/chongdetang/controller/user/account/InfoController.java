package com.cdtde.chongdetang.controller.user.account;

import com.cdtde.chongdetang.service.user.InfoSerevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {
    @Autowired
    private InfoSerevice infoSerevice;

    @GetMapping("/api/user/account/info/")
    public Map<String,String> getinfo(){
        return infoSerevice.getinfo();
    }
}

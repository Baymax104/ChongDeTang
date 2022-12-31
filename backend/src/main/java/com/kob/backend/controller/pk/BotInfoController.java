package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getbotinfo/")
    public HashMap<String, String> getBotInfo(){
        HashMap<String,String> m= new HashMap<>();
        m.put("name","clb");
        m.put("rating","1500");
        return m;
    }
}

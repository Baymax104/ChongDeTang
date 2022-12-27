package com.cdtde.chongdetang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/28 2:06
 * @Version 1
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello SprintBoot";
    }
}

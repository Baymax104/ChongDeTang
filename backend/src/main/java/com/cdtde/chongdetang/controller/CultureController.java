package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Culture;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.CultureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/culture")
public class CultureController {
    @Autowired
    private CultureService cultureService;

    @GetMapping("/{type}")
    public Result<List<Culture>> download(@PathVariable("type") String type) {
        return cultureService.download(type);
    }

    @PostMapping("/{type}")
    public Result<Object> upload(@PathVariable("type") String type, @RequestBody Map<String, String> map) {
        String date = map.get("date");
        String title = map.get("title");
        String url = map.get("url");
        String description = map.get("description");
        String photo = map.get("photo");
        return cultureService.upload(type, date, title, url, description, photo);
    }
}
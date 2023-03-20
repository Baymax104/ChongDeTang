package com.cdtde.chongdetang.controller;


import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/{type}")
    public ResponseResult<List<News>> download(@PathVariable("type") String type) {
        return newsService.download(type);
    }

    @PostMapping("/{type}")
    public ResponseResult<Object> upload(@PathVariable("type") String type, @RequestBody Map<String, String> map) {
        String date = map.get("date");
        String title = map.get("title");
        String photo = map.get("photo");
        String url = map.get("url");
        String description = map.get("description");

        return newsService.upload(date, title, photo, url, type, description);
    }


}

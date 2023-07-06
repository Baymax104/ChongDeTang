package com.cdtde.chongdetang.controller;


import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.Result;
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
    public Result<List<News>> getNews(@PathVariable("type") String type) {
        return newsService.getNews(type);
    }

    @PostMapping("/{type}")
    public Result<Object> addNews(@PathVariable("type") String type, @RequestBody News news) {
        news.setType(type);
        return newsService.addNews(news);
    }


}

package com.cdtde.chongdetang.controller;


import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/{type}")
    public ResponseResult<List<News>> download(@PathVariable("type") String type){
        return newsService.download(type);
    }
}

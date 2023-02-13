package com.cdtde.chongdetang.controller;


import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/download")
    public ResponseResult<List<News>> download(@RequestBody Map<String, String> map){
        String type = map.get("type");
        return newsService.download(type);
    }
}

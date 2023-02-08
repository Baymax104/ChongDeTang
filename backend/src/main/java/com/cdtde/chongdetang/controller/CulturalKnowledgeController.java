package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.CulturalKnowledge;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CulturalKnowledgeService;
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
@RequestMapping("/api/culturalknowledge")
public class CulturalKnowledgeController {
    @Autowired
    private CulturalKnowledgeService culturalKnowledgeService;

    @PostMapping("/upload")
    public ResponseResult<Object> upload(@RequestBody Map<String, String> map) {
        String type = map.get("type");
        String date = map.get("date");
        String title = map.get("title");
        String photo = map.get("photo");
        String url = map.get("url");
        String aabstract = map.get("abstract");

        return culturalKnowledgeService.upload(type,date,title,photo,url,aabstract);
    }

    @PostMapping("/download")
    public ResponseResult<List<CulturalKnowledge>> download(@RequestBody Map<String, String> map){
        String type = map.get("type");
        return culturalKnowledgeService.download(type);
    }
}
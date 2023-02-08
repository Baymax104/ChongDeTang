package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.ExhibitionCenter;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.ExhibitionCenterService;
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
@RequestMapping("/api/exhibitioncenter")
public class ExhibitionCenterController {
    @Autowired
    private ExhibitionCenterService exhibitionCenterService;

    @PostMapping("/upload")
    public ResponseResult<Object> upload(@RequestBody Map<String, String> map) {
        String type = map.get("type");
        String title = map.get("title");
        String photo = map.get("photo");
        String url = map.get("url");

        return exhibitionCenterService.upload(type,title,photo,url);
    }

    @PostMapping("/download")
    public ResponseResult<List<ExhibitionCenter>> download(@RequestBody Map<String, String> map){
        String type = map.get("type");
        return exhibitionCenterService.download(type);
    }
}
package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Cultural;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CulturalService;
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
@RequestMapping("/api/cultural")
public class CulturalController {
    @Autowired
    private CulturalService culturalService;

    @PostMapping("/download")
    public ResponseResult<List<Cultural>> download(@RequestBody Map<String, String> map){
        String type = map.get("type");
        return culturalService.download(type);
    }
}
package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Culture;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CultureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/culture")
public class CultureController {
    @Autowired
    private CultureService cultureService;

    @GetMapping
    public ResponseResult<List<Culture>> download(){
        return cultureService.download();
    }
}
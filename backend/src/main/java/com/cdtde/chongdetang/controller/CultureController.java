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
    public Result<List<Culture>> getCultures(@PathVariable("type") String type) {
        return cultureService.getCultures(type);
    }

    @PostMapping("/{type}")
    public Result<Object> addCulture(@PathVariable("type") String type, @RequestBody Culture culture) {
        culture.setType(type);
        return cultureService.addCulture(culture);
    }
}
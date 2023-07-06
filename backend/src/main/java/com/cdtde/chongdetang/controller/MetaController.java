package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/meta")
public class MetaController {
    @Autowired
    private ProductService productService;

    @GetMapping("/basis")
    public Result<Map<String,Integer>> getNums() {
        return productService.getNums();
    }

    @PostMapping("/order")
    public Result<List<Map<String, Double>>> getOrderInfo(@RequestBody Map<String, Integer> map){
        return productService.getOrderInfo(map.get("days"));
    }
}

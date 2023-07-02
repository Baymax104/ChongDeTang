package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.Product;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 搜索Controller
 * @Author John
 * @Date 2023/7/2 20:32
 * @Version 1
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService service;

    /**
     * 搜索藏品
     * @param content 输入内容，URL后缀参数
     * @return 藏品结果列表
     */
    @GetMapping("/collection")
    public Result<List<Collection>> searchCollection(@RequestParam("content") String content) {
        return service.searchCollection(content);
    }

    /**
     * 搜索商品
     * @param content 输入内容，URL后缀参数
     * @return 商品结果列表
     */
    @GetMapping("/product")
    public Result<List<Product>> searchProduct(@RequestParam("content") String content) {
        return service.searchProduct(content);
    }
}

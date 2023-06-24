package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.*;
import com.cdtde.chongdetang.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/19 22:04
 * @Version 1
 */
@RestController
@RequestMapping("/api/user/collect")
public class UserCollectController {

    @Autowired
    private UserCollectService userCollectService;

    @GetMapping("/collection")
    public Result<List<Collection>> getUserCollection() {
        return userCollectService.getUserCollection();
    }

    @GetMapping("/product")
    public Result<List<Product>> getUserProduct() {
        return userCollectService.getUserProduct();
    }

    @PostMapping("/collection")
    public Result<Object> addUserCollection(@RequestBody Collection collection) {
        return userCollectService.addUserCollection(collection);
    }

    @DeleteMapping("/collection")
    public Result<Object> removeUserCollection(@RequestBody Collection collection) {
        return userCollectService.removeUserCollection(collection);
    }

    @PostMapping("/product")
    public Result<Object> addUserProduct(@RequestBody Product product) {
        return userCollectService.addUserProduct(product);
    }

    @DeleteMapping("/product")
    public Result<Object> removeUserProduct(@RequestBody Product product) {
        return userCollectService.removeUserProduct(product);
    }

}

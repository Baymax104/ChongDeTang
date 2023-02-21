package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Product;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.Shopping;
import com.cdtde.chongdetang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/9 22:06
 * @Version 1
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseResult<List<Product>> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/shopping")
    public ResponseResult<List<Shopping>> getShoppingByUser() {
        return productService.getShoppingByUser();
    }

    @PostMapping("/shopping/{shoppingId}/{productId}")
    public ResponseResult<Integer> updateShoppingNumber(@PathVariable("shoppingId") Integer shoppingId,
                                                       @PathVariable("productId") Integer productId,
                                                       @RequestParam("number") Integer number) {
        return productService.updateShoppingNumber(shoppingId, productId, number);
    }

    @PostMapping("/shopping")
    public ResponseResult<Object> addShopping(@RequestBody Shopping shopping) {
        return productService.addShopping(shopping);
    }

    @DeleteMapping("/shopping")
    public ResponseResult<Object> deleteShopping(@RequestBody Shopping shopping) {
        return productService.deleteShopping(shopping);
    }
}

package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Product;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.pojo.Shopping;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/9 23:02
 * @Version 1
 */
public interface ProductService {

    Result<List<Product>> getAllProduct();

    Result<List<Product>> getHotProduct();

    // 用户与购物车交互
    Result<List<Shopping>> getShoppingByUser();

    Result<Integer> updateShoppingNumber(Integer shoppingId, Integer productId, Integer number);

    Result<Object> addShopping(Shopping shopping);

    Result<Object> removeShopping(Shopping shopping);

    // 管理员交互
    Result<List<Product>> getAllProductByAdmin();

    Result<Object> updateProductByAdmin(Integer productId, Product product);

    Result<Object> addProductByAdmin(Product product);

    Result<Object> removeProductByAdmin(Product product);


}

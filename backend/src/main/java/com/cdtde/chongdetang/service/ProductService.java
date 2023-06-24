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

    Result<List<Shopping>> getShoppingByUser();

    Result<Integer> updateShoppingNumber(Integer shoppingId, Integer productId, Integer number);

    Result<Object> addShopping(Shopping shopping);

    Result<Object> deleteShopping(Shopping shopping);
}

package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Product;
import com.cdtde.chongdetang.pojo.ResponseResult;
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

    ResponseResult<List<Product>> getAllProduct();
    ResponseResult<List<Shopping>> getShoppingByUser();
    ResponseResult<Integer> updateShoppingNumber(Integer shoppingId, Integer productId, Integer number);
    ResponseResult<Object> addShopping(Shopping shopping);
    ResponseResult<Object> deleteShopping(Shopping shopping);
}

package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Product;
import com.cdtde.chongdetang.pojo.ResponseResult;

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
}

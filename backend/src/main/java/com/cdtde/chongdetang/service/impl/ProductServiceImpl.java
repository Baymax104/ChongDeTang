package com.cdtde.chongdetang.service.impl;

import com.cdtde.chongdetang.mapper.ProductMapper;
import com.cdtde.chongdetang.pojo.Product;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/9 23:03
 * @Version 1
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseResult<List<Product>> getAllProduct() {
        List<Product> products = productMapper.selectList(null);
        return new ResponseResult<>("success", null, products);
    }
}

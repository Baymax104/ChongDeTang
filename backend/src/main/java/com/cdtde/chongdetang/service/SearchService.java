package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.Product;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

/**
 * @Description 搜索Service
 * @Author John
 * @Date 2023/7/2 20:55
 * @Version 1
 */
public interface SearchService {

    /**
     * 搜索藏品
     * @param content 输入内容
     * @return 藏品结果列表
     */
    Result<List<Collection>> searchCollection(String content);

    /**
     * 搜索商品
     * @param content 输入内容
     * @return 商品结果列表
     */
    Result<List<Product>> searchProduct(String content);

}

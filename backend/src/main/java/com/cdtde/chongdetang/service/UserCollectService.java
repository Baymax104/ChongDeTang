package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.*;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/19 22:06
 * @Version 1
 */
public interface UserCollectService {
    Result<List<Collection>> getUserCollection();

    Result<List<Product>> getUserProduct();

    Result<Object> addUserCollection(Collection collection);

    Result<Object> removeUserCollection(Collection collection);

    Result<Object> addUserProduct(Product product);

    Result<Object> removeUserProduct(Product product);

}

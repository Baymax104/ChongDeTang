package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.UserCollectionMapper;
import com.cdtde.chongdetang.mapper.UserProductMapper;
import com.cdtde.chongdetang.pojo.*;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/19 22:08
 * @Version 1
 */
@Service
public class UserCollectServiceImpl implements UserCollectService {

    @Autowired
    private UserCollectionMapper collectionMapper;

    @Autowired
    private UserProductMapper productMapper;

    @Override
    public Result<List<Collection>> getUserCollection() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        List<UserCollection> userCollects = collectionMapper.getUserCollection(userId);

        List<Collection> collections = userCollects.stream()
                .map(UserCollection::getCollection)
                .collect(Collectors.toList());
        collections.forEach(collection -> collection.setUserCollect("1"));

        return new Result<>("success", null, collections);
    }

    @Override
    public Result<List<Product>> getUserProduct() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        List<UserProduct> userProducts = productMapper.getUserProduct(userId);

        List<Product> products = userProducts.stream()
                .map(UserProduct::getProduct)
                .collect(Collectors.toList());

        products.forEach(product -> product.setUserCollect("1"));

        return new Result<>("success", null, products);
    }

    @Override
    public Result<Object> addUserCollection(Collection collection) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        UserCollection userCollection = new UserCollection()
                .setUserId(userId)
                .setCollection(collection);

        int i = collectionMapper.insertUserCollection(userCollection);
        if (i != 1) {
            throw new RuntimeException("添加用户收藏错误");
        }

        return new Result<>("success", null, null);
    }

    @Override
    public Result<Object> removeUserCollection(Collection collection) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        QueryWrapper<UserCollection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("collection_id", collection.getId());

        int delete = collectionMapper.delete(wrapper);
        if (delete != 1) {
            throw new RuntimeException("取消收藏错误");
        }

        return new Result<>("success", null, null);
    }

    @Override
    public Result<Object> addUserProduct(Product product) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        UserProduct userProduct = new UserProduct()
                .setUserId(userId)
                .setProduct(product);

        int i = productMapper.insertUserProduct(userProduct);
        if (i != 1) {
            throw new RuntimeException("添加用户收藏错误");
        }

        return new Result<>("success", null, null);
    }

    @Override
    public Result<Object> removeUserProduct(Product product) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        QueryWrapper<UserProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("product_id", product.getId());

        int delete = productMapper.delete(wrapper);
        if (delete != 1) {
            throw new RuntimeException("取消收藏错误");
        }

        return new Result<>("success", null, null);
    }
}

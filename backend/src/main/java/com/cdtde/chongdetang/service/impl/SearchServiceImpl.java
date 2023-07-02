package com.cdtde.chongdetang.service.impl;

import com.cdtde.chongdetang.mapper.CollectionMapper;
import com.cdtde.chongdetang.mapper.ProductMapper;
import com.cdtde.chongdetang.mapper.UserCollectionMapper;
import com.cdtde.chongdetang.mapper.UserProductMapper;
import com.cdtde.chongdetang.pojo.*;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description {@link SearchService}实现类
 * @see SearchService
 * @Author John
 * @Date 2023/7/2 21:00
 * @Version 1
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CollectionMapper collectionMapper;
    
    @Autowired
    private UserCollectionMapper userCollectionMapper;
    
    @Autowired
    private UserProductMapper userProductMapper;

    @Override
    public Result<List<Collection>> searchCollection(String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 搜索，正则匹配包含content的名称
        List<Collection> collections = collectionMapper.selectList(null);
        String pattern = ".*" + content + ".*";
        List<Collection> result = collections.stream()
                .filter(collection -> Pattern.matches(pattern, collection.getTitle()))
                .collect(Collectors.toList());


        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("success", null, result);
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        List<UserCollection> userCollection = userCollectionMapper.getUserCollection(userId);

        for (UserCollection uc : userCollection) {
            for (Collection c : result) {
                if (ObjectUtils.nullSafeEquals(uc.getCollection().getId(), c.getId())) {
                    c.setUserCollect("1");
                    break;
                }
            }
        }

        return new Result<>("success", null, result);
    }

    @Override
    public Result<List<Product>> searchProduct(String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 搜索，正则匹配包含content的名称
        List<Product> products = productMapper.selectList(null);
        String pattern = ".*" + content + ".*";
        List<Product> result = products.stream()
                .filter(product -> Pattern.matches(pattern, product.getName()))
                .collect(Collectors.toList());


        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("success", null, result);
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        List<UserProduct> userProduct = userProductMapper.getUserProduct(userId);

        for (UserProduct up : userProduct) {
            for (Product c : result) {
                if (ObjectUtils.nullSafeEquals(up.getProduct().getId(), c.getId())) {
                    c.setUserCollect("1");
                    break;
                }
            }
        }

        return new Result<>("success", null, result);
    }
}

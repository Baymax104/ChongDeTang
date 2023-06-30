package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.ProductMapper;
import com.cdtde.chongdetang.mapper.ShoppingMapper;
import com.cdtde.chongdetang.mapper.UserProductMapper;
import com.cdtde.chongdetang.pojo.*;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

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

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Autowired
    private UserProductMapper userProductMapper;

    @Override
    public Result<List<Product>> getAllProduct() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Product> products = productMapper.selectList(null);

        // 未登录
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("success", null, products);
        }

        // 已登录，获取收藏状态
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        List<UserProduct> userProducts = userProductMapper.getUserProduct(userId);

        for (UserProduct up : userProducts) {
            for (Product p : products) {
                if (ObjectUtils.nullSafeEquals(up.getProduct().getId(), p.getId())) {
                    p.setUserCollect("1");
                    break;
                }
            }
        }

        return new Result<>("success", null, products);
    }

    @Override
    public Result<List<Product>> getHotProduct() {
        List<Product> allProducts = productMapper.selectList(null);
        List<Product> hotProducts;
        if (allProducts.size() >= 3) {
            Set<Product> set = new HashSet<>();
            while (set.size() < 3) {
                int index = new Random().nextInt(allProducts.size());
                set.add(allProducts.get(index));
            }
            hotProducts = new ArrayList<>(set);
        } else {
            hotProducts = allProducts;
        }
        return new Result<>("success", null, hotProducts);
    }

    @Override
    public Result<List<Shopping>> getShoppingByUser() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();
        List<Shopping> shopping = shoppingMapper.selectByOneKey(userId, null);
        return new Result<>("success", null, shopping);
    }

    @Override
    public Result<Integer> updateShoppingNumber(Integer shoppingId, Integer productId, Integer number) {
        Result<Integer> result = new Result<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        UpdateWrapper<Shopping> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", shoppingId)
                .eq("user_id", userId)
                .eq("product_id", productId)
                .set("number", number);
        int update = shoppingMapper.update(null, wrapper);
        if (update != 1) {
            throw new RuntimeException("修改购物车商品数量错误");
        }
        result.setStatus("success").setData(number);
        return result;
    }

    @Override
    public Result<Object> addShopping(Shopping shopping) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();
        shopping.setUserId(userId);

        Shopping s = shoppingMapper.selectByAllKey(userId, shopping.getProduct().getId());
        // 若不存在则添加，否则增加数量
        int i;
        if (s == null) {
            i = shoppingMapper.insertShopping(shopping);
        } else {
            s.setNumber(s.getNumber() + shopping.getNumber());
            UpdateWrapper<Shopping> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", s.getId())
                    .eq("user_id", s.getUserId())
                    .eq("product_id", s.getProduct().getId())
                    .set("number", s.getNumber());
            i = shoppingMapper.update(null, wrapper);
        }
        if (i != 1) {
            throw new RuntimeException("添加购物车失败");
        }

        return new Result<>("success", null, null);
    }

    @Override
    public Result<Object> deleteShopping(Shopping shopping) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        if (userId != shopping.getUserId()) {
            throw new RuntimeException("用户信息错误");
        }

        int i = shoppingMapper.deleteById(shopping);
        if (i != 1) {
            throw new RuntimeException("删除购物车失败");
        }

        return new Result<>("success", null, null);
    }
}

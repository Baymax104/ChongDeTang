package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.ProductMapper;
import com.cdtde.chongdetang.mapper.ShoppingMapper;
import com.cdtde.chongdetang.pojo.Product;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.Shopping;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Override
    public ResponseResult<List<Product>> getAllProduct() {
        List<Product> products = productMapper.selectList(null);
        return new ResponseResult<>("success", null, products);
    }

    @Override
    public ResponseResult<List<Shopping>> getShoppingByUser() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();
        List<Shopping> shopping = shoppingMapper.selectByOneKey(userId, null);
        return new ResponseResult<>("success", null, shopping);
    }

    @Override
    public ResponseResult<Integer> updateShoppingNumber(Integer shoppingId, Integer productId, Integer number) {
        ResponseResult<Integer> result = new ResponseResult<>();
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
    public ResponseResult<Object> addShopping(Shopping shopping) {
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

        return new ResponseResult<>("success", null, null);
    }

    @Override
    public ResponseResult<Object> deleteShopping(Shopping shopping) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        if (userId != shopping.getUserId()) {
            throw new RuntimeException("用户信息错误");
        }

        int i = shoppingMapper.deleteById(shopping);
        if (i != 1) {
            throw new RuntimeException("删除购物车失败");
        }

        return new ResponseResult<>("success", null, null);
    }
}

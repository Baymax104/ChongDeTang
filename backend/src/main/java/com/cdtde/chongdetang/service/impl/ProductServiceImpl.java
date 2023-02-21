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
        List<Shopping> shopping = shoppingMapper.getShoppingByUserId(userId);
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
}

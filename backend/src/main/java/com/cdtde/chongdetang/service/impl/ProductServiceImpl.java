package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.*;
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

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private UserMapper userMapper;

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

    public Result<List<Product>> getAllProductByAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            List<Product> products = productMapper.selectList(null);
//            products.forEach(product -> {
//                if (product.getPhoto() != null) {
//                    product.setPhoto(urlFront + '/' + product.getPhoto());
//                }
//            });
            // 统计收藏数
            Map<Integer, Integer> counts = new HashMap<>();
            List<UserProduct> userProducts = userProductMapper.selectList(null);
            userProducts.stream()
                    .map(UserProduct::getProduct)
                    .map(Product::getId)
                    .forEach(id -> {
                        Integer count = counts.getOrDefault(id, 0);
                        counts.put(id, count + 1);
                    });
            // 赋值userCollect
            products.forEach(p -> {
                String count = counts.getOrDefault(p.getId(), 0).toString();
                p.setUserCollect(count);
            });

            return new Result<>("success",null,products);
        }
        return new Result<>("error", "您没有管理员权限", null);
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
    public Result<Object> updateProductByAdmin(Integer productId, Product product) {
        Result<Object> result = new Result<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            UpdateWrapper<Product> wrapper = new UpdateWrapper<>();
            wrapper.eq("id",productId)
                    .set("storage",product.getStorage())
                    .set("photo",product.getPhoto())
                    .set("Introduction",product.getIntroduction())
                    .set("price",product.getPrice())
                    .set("launch_time",product.getLaunchTime())
                    .set("name",product.getName());

            int update = productMapper.update(null,wrapper);
            if (update != 1) {
                throw new RuntimeException("修改商品错误");
            }
            result.setStatus("success").setData(null);
            return result;
        }
        result.setStatus("error");
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
    public Result<Object> addProductByAdmin(Product product) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",product.getName());
            List<Product> product1 = productMapper.selectList(queryWrapper);
            if(!product1.isEmpty()){
                throw new RuntimeException("存在同名商品");
            }
            int i = productMapper.insert(product);
            if(i != 1){
                throw new RuntimeException("添加商品失败");
            }
            return new Result<>("success",null,null);
        }

        return new Result<>("error", "您没有管理员权限", null);
    }

    @Override
    public Result<Object> removeShopping(Shopping shopping) {
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

    @Override
    public Result<Object> removeProductByAdmin(Product product) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            int i = productMapper.deleteById(product);
            if(i!=1){
                throw new RuntimeException("删除商品失败");
            }
        }
        return new Result<>("success", null, null);
    }

    @Override
    public Result<Map<String ,Integer>> getNumsByAdmin(){
        Map<String ,Integer> result = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            result.put("product",productMapper.selectList(null).size());
            result.put("collection",collectionMapper.selectList(null).size());
            result.put("user",userMapper.selectList(null).size());

        }
        return new Result<>("success", null, result);
    }


}

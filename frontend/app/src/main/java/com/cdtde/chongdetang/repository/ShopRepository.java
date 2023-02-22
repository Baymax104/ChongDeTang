package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.dataSource.web.WebException;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.ProductService;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.entity.User;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 1:24
 * @Version 1
 */
@SuppressLint("CheckResult")
public class ShopRepository {

    private UserRepository userRepo;
    private static ShopRepository repository;

    private List<Integer> bannerResource;

    private List<Product> products;

    private List<Product> hotProducts;

    private List<Shopping> shoppings;

    private ProductService productService;

    private ShopRepository() {
        userRepo = UserRepository.getInstance();

        bannerResource = new ArrayList<>();
        bannerResource.add(R.drawable.shop_banner1);
        bannerResource.add(R.drawable.shop_banner2);
        bannerResource.add(R.drawable.shop_banner3);

        products = new ArrayList<>();
        hotProducts = new ArrayList<>();
        shoppings = new ArrayList<>();

        productService = WebService.getInstance().create(ProductService.class);
    }

    public static ShopRepository getInstance() {
        if (repository == null) {
            repository = new ShopRepository();
        }
        return repository;
    }

    public User getUser() {
        return userRepo.getUser();
    }

    public List<Integer> getBannerResource() {
        return bannerResource;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getHotProducts() {
        return hotProducts;
    }

    public List<Shopping> getShoppings() {
        return shoppings;
    }

    public void updateHotProduct() {
        if (products.size() >= 3) {
            Set<Product> set = new HashSet<>();
            while (set.size() < 3) {
                int index = new Random().nextInt(products.size());
                set.add(products.get(index));
            }
            hotProducts = new ArrayList<>(set);
        }
    }

    public void requestAllProduct() {
        Consumer<ResponseResult<List<Product>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                products = result.getData();
                updateHotProduct();
                LogUtils.iTag("cdt-web-requestAllProduct", "获取商品成功");
            } else {
                LogUtils.eTag("cdt-web-requestAllProduct", result.getMessage());
            }
            LiveEventBus.get("ShopRepository-requestAllProduct", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        productService.getAllProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("requestAllProduct", "ShopRepository-requestAllProduct"),
                        () -> LogUtils.iTag("cdt-web-requestAllProduct", "获取商品请求结束")
                );
    }

    public void requestShopping() {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<Shopping>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                shoppings = result.getData();
                LogUtils.iTag("cdt-web-requestShopping", "获取购物车成功");
            } else {
                LogUtils.eTag("cdt-web-requestShopping", result.getMessage());
            }
            LiveEventBus.get("ShopRepository-requestShopping", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        productService.getShoppingByUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("requestShopping", "ShopRepository-requestShopping"),
                        () -> LogUtils.iTag("cdt-web-requestShopping", "获取购物车请求结束")
                );
    }

    public void updateShoppingNumber(Shopping shopping, Integer number) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Integer>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                shopping.setNumber(result.getData());
                LogUtils.iTag("cdt-web-updateShoppingNumber", "修改商品数量成功");
            } else {
                LogUtils.eTag("cdt-web-updateShoppingNumber", result.getMessage());
            }
            LiveEventBus.get("ShopRepository-updateShoppingNumber", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        productService.updateShoppingNumber(token, shopping.getId(), shopping.getProduct().getId(), number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("updateShoppingNumber", "ShopRepository-updateShoppingNumber"),
                        () -> LogUtils.iTag("cdt-web-updateShoppingNumber", "修改商品数量请求结束")
                );
    }

    public void addShopping(Shopping shopping) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                LogUtils.iTag("cdt-web-addShopping", "添加购物车成功");
            } else {
                LogUtils.eTag("cdt-web-addShopping", result.getMessage());
            }
            LiveEventBus.get("ShopRepository-addShopping", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        productService.addShopping(token, shopping)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("addShopping", "ShopRepository-addShopping"),
                        () -> LogUtils.iTag("cdt-web-addShopping", "添加购物车请求结束")
                );
    }

    public void deleteShopping(Shopping shopping) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                LogUtils.iTag("cdt-web-deleteShopping", "删除购物车成功");
            } else {
                LogUtils.eTag("cdt-web-deleteShopping", result.getMessage());
            }
            LiveEventBus.get("ShopRepository-deleteShopping", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        productService.deleteShopping(token, shopping)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("deleteShopping", "ShopRepository-deleteShopping"),
                        () -> LogUtils.iTag("cdt-web-deleteShopping", "删除购物车请求结束")
                );
    }

}

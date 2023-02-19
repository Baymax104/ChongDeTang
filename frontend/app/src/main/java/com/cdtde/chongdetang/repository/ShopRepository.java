package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.ProductService;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.ResponseResult;
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

    private ProductService productService;

    private ShopRepository() {
        userRepo = UserRepository.getInstance();

        bannerResource = new ArrayList<>();
        bannerResource.add(R.drawable.shop_banner1);
        bannerResource.add(R.drawable.shop_banner2);
        bannerResource.add(R.drawable.shop_banner3);

        products = new ArrayList<>();
        hotProducts = new ArrayList<>();

        productService = WebService.getInstance().create(ProductService.class);
    }

    public static ShopRepository getInstance() {
        if (repository == null) {
            repository = new ShopRepository();
        }
        return repository;
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
            if (result.getStatus().equals("success")) {
                if (result.getData() != null) {
                    products = result.getData();
                    updateHotProduct();
                    LogUtils.iTag("cdt-web-requestAllProduct", "获取商品成功");
                    LiveEventBus.get("ShopRepository-requestAllProduct", Boolean.class).post(true);
                }
            } else {
                LogUtils.eTag("cdt-web-requestAllProduct", result.getMessage());
            }
        };

        productService.getAllProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-requestAllProduct", throwable),
                        () -> LogUtils.iTag("cdt-web-requestAllProduct", "获取商品请求结束")
                );
    }

}

package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.ProductService;
import com.cdtde.chongdetang.dataSource.web.api.UserService;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.exception.WebException;
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

    private UserService userService;

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
        userService = WebService.getInstance().create(UserService.class);
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
        String token = userRepo.getUser().getToken() != null ?
                WebService.TOKEN_PREFIX + userRepo.getUser().getToken() : null;

        Consumer<ResponseResult<List<Product>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                products = result.getData();
                updateHotProduct();
            }
            LiveEventBus.get("ShopRepository-requestAllProduct", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("ShopRepository-requestAllProduct", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        productService.getAllProduct(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestShopping() {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<Shopping>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                shoppings = result.getData();
            }
            LiveEventBus.get("ShopRepository-requestShopping", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("ShopRepository-requestShopping", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        productService.getShoppingByUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestUpdateShoppingNumber(Shopping shopping, Integer number) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<Integer>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                shopping.setNumber(result.getData());
            }
            LiveEventBus.get("ShopRepository-requestUpdateShoppingNumber", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("ShopRepository-requestUpdateShoppingNumber", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        productService.updateShoppingNumber(token, shopping.getId(), shopping.getProduct().getId(), number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestAddShopping(Shopping shopping) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            LiveEventBus.get("ShopRepository-requestAddShopping", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("ShopRepository-requestAddShopping", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        productService.addShopping(token, shopping)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestDeleteShopping(Shopping shopping) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            LiveEventBus.get("ShopRepository-requestDeleteShopping", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("ShopRepository-requestDeleteShopping", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        productService.deleteShopping(token, shopping)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestAddUserCollect(UserCollect userCollect) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();
        userCollect.setUserId(userRepo.getUser().getId());

        String eventKey = "ShopRepository-requestAddUserCollect";

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                userCollect.getProduct().setUserCollect(true);
            }
            LiveEventBus.get(eventKey, WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get(eventKey, WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        userService.addUserCollect(token, userCollect)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestRemoveUserCollect(UserCollect userCollect) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();
        userCollect.setUserId(userRepo.getUser().getId());

        String event = "ShopRepository-requestRemoveUserCollect";

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                userCollect.getProduct().setUserCollect(false);
            }
            LiveEventBus.get(event, WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get(event, WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        userService.removeUserCollect(token, userCollect)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

}

package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.ProductService;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.Shopping;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 1:24
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class ProductRepository {

    private static ProductRepository repository;

    private ProductService productService = WebService.create(ProductService.class);


    private ProductRepository() {
    }

    public static ProductRepository getInstance() {
        if (repository == null) {
            repository = new ProductRepository();
        }
        return repository;
    }

    public void requestHotProduct(ReqCallback<List<Product>> callback) {
        productService.getHotProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new Exception(res.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }

    public void requestAllProduct(ReqCallback<List<Product>> callback) {
        String token = UserStore.getUser().getToken() != null ?
                WebService.TOKEN_PREFIX + UserStore.getUser().getToken() : null;

        productService.getAllProduct(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new Exception(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }

    public void requestShopping(ReqCallback<List<Shopping>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        productService.getShoppingByUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new Exception(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }

    public void requestUpdateShoppingNumber(Shopping shopping, Integer number, ReqCallback<Integer> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        productService.updateShoppingNumber(token, shopping.getId(), shopping.getProduct().getId(), number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new Exception(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }

    public void requestAddShopping(Shopping shopping, ReqCallback<Object> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        productService.addShopping(token, shopping)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(new Object()) :
                        Single.error(new Exception(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }

    public void requestDeleteShopping(Shopping shopping, ReqCallback<List<Shopping>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        productService.deleteShopping(token, shopping)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .flatMap(result -> result.isSuccess() ?
                        productService.getShoppingByUser(token) :
                        Single.error(new Exception(result.getMessage())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new Exception(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }

}

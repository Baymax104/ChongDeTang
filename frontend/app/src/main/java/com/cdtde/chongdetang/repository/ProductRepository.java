package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.ProductService;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.exception.WebException;

import java.net.SocketTimeoutException;
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
                        Single.error(new WebException(res.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess,
                        throwable -> {
                            if (throwable instanceof SocketTimeoutException) {
                                callback.onFail.accept("网络出了点小问题~");
                            } else if (throwable instanceof WebException) {
                                callback.onFail.accept("服务器出了点小问题~");
                            } else {
                                callback.onFail.accept(throwable.getMessage());
                            }
                        });
    }

    public void requestAllProduct(ReqCallback<List<Product>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getUser().getToken();

        productService.getAllProduct(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> {
                            if (throwable instanceof SocketTimeoutException) {
                                callback.onFail.accept("网络出了点小问题~");
                            } else if (throwable instanceof WebException) {
                                callback.onFail.accept("服务器出了点小问题~");
                            } else {
                                callback.onFail.accept(throwable.getMessage());
                            }
                        });
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
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> {
                            if (throwable instanceof SocketTimeoutException) {
                                callback.onFail.accept("网络出了点小问题~");
                            } else if (throwable instanceof WebException) {
                                callback.onFail.accept("服务器出了点小问题~");
                            } else {
                                callback.onFail.accept(throwable.getMessage());
                            }
                        });
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
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> {
                            if (throwable instanceof SocketTimeoutException) {
                                callback.onFail.accept("网络出了点小问题~");
                            } else if (throwable instanceof WebException) {
                                callback.onFail.accept("服务器出了点小问题~");
                            } else {
                                callback.onFail.accept(throwable.getMessage());
                            }
                        });
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
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess,
                        throwable -> {
                            if (throwable instanceof SocketTimeoutException) {
                                callback.onFail.accept("网络出了点小问题~");
                            } else if (throwable instanceof WebException) {
                                callback.onFail.accept("服务器出了点小问题~");
                            } else {
                                callback.onFail.accept(throwable.getMessage());
                            }
                        });
    }

    public void requestDeleteShopping(Shopping shopping, ReqCallback<Object> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        productService.deleteShopping(token, shopping)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(new Object()) :
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

}

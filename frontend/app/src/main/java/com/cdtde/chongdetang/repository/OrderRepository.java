package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.OrderService;
import com.cdtde.chongdetang.entity.Order;
import com.cdtde.chongdetang.exception.WebException;

import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName OrderRepository
 * @Author John
 * @Date 2023/7/3 15:52
 * @Version 1.0
 */
@SuppressWarnings("CheckResult")
public class OrderRepository {

    private OrderService service = WebService.create(OrderService.class);

    private static OrderRepository instance;

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public void requestAllOrders(ReqCallback<List<Order>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.getAllOrders(token)
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

    public void requestAddOrder(Order order, ReqCallback<Object> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.addOrder(token, order)
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

    public void requestRemoveOrder(Order order, ReqCallback<List<Order>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.removeOrder(token, order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .flatMap(res -> res.isSuccess() ?
                        service.getAllOrders(token)
                                .subscribeOn(Schedulers.io()) :
                        Single.error(new WebException(res.getMessage())))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }
}

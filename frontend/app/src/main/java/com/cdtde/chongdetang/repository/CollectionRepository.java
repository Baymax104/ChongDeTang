package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.CollectionService;
import com.cdtde.chongdetang.entity.Collection;
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
 * @Date 2023/1/12 23:22
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class CollectionRepository {

    private static CollectionRepository repository;

    private CollectionService collectionService = WebService.create(CollectionService.class);

    private CollectionRepository() {
    }

    public static CollectionRepository getInstance() {
        if (repository == null) {
            repository = new CollectionRepository();
        }
        return repository;
    }


    public void requestCollectionByType(String type, ReqCallback<List<Collection>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        collectionService.getAllCollection(token, type)
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

    public void requestHotCollection(ReqCallback<List<Collection>> callback) {

        collectionService.getHotCollection()
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

}

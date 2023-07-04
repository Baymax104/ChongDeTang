package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.UserCollectService;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
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
 * @Date 2023/6/19 23:14
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class UserCollectRepository {

    private UserCollectService service = WebService.create(UserCollectService.class);

    private static UserCollectRepository instance;

    private UserCollectRepository() {
    }

    public static UserCollectRepository getInstance() {
        if (instance == null) {
            instance = new UserCollectRepository();
        }
        return instance;
    }

    public void requestUserCollection(ReqCallback<List<Collection>> callback) {

        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.getUserCollection(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> result.isSuccess() ?
                        Single.just(result.getData()) :
                        Single.error(new WebException(result.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess, callback::baseHandleException);

    }

    public void requestUserProduct(ReqCallback<List<Product>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.getUserProduct(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> result.isSuccess() ?
                        Single.just(result.getData()) :
                        Single.error(new WebException(result.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

    public void requestAddUserCollection(Collection collection, ReqCallback<Collection> callback) {

        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.addUserCollection(token, collection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(result -> result.isSuccess() ?
                        Single.just(collection) :
                        Single.error(new WebException(result.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

    public void requestAddUserProduct(Product product, ReqCallback<Product> callback) {

        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.addUserProduct(token, product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(result -> result.isSuccess() ?
                        Single.just(product) :
                        Single.error(new WebException(result.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

    public void requestRemoveUserCollection(Collection collection, ReqCallback<List<Collection>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.removeUserCollection(token, collection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .flatMap(result -> result.isSuccess() ?
                        service.getUserCollection(token)
                                .subscribeOn(Schedulers.io()):
                        Single.error(new WebException(result.getMessage())))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

    public void requestRemoveUserCollectionWithoutList(Collection collection,
                                                       ReqCallback<Collection> callback) {

        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.removeUserCollection(token, collection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(collection) :
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

    public void requestRemoveUserProduct(Product product, ReqCallback<List<Product>> callback) {

        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.removeUserProduct(token, product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .flatMap(result -> result.isSuccess() ?
                        service.getUserProduct(token)
                                .subscribeOn(Schedulers.io()):
                        Single.error(new WebException(result.getMessage())))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(result -> result.isSuccess() ?
                        Single.just(result.getData()) :
                        Single.error(new WebException(result.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

    public void requestRemoveUserProductWithoutList(Product product,
                                                    ReqCallback<Product> callback) {

        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.removeUserProduct(token, product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(result -> result.isSuccess() ?
                        Single.just(product) :
                        Single.error(new WebException(result.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

}

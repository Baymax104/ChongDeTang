package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.AddressService;
import com.cdtde.chongdetang.entity.Address;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/20 0:35
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class AddressRepository {

    private AddressService service = WebService.create(AddressService.class);

    private static AddressRepository instance;

    private AddressRepository() {
    }

    public static AddressRepository getInstance() {
        if (instance == null) {
            instance = new AddressRepository();
        }
        return instance;
    }

    public void requestAllAddress(ReqCallback<List<Address>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.getAllAddress(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> result.isSuccess() ?
                        Single.just(result.getData()) :
                        Single.error(new Exception(result.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }

    public void requestUpdateAddress(Address address, ReqCallback<Address> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.updateAddress(token, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> result.isSuccess() ?
                        Single.just(address) :
                        Single.error(new Exception(result.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }

    public void requestRemoveAddress(Address address, ReqCallback<Address> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.deleteAddress(token, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> result.isSuccess() ?
                        Single.just(address) :
                        Single.error(new Exception(result.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));
    }


}

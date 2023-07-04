package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.SearchService;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.exception.WebException;

import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName SearchRepository
 * @Author John
 * @Date 2023/7/2 16:40
 * @Version 1.0
 */
@SuppressWarnings("CheckResult")
public class SearchRepository {

    private final SearchService service = WebService.create(SearchService.class);

    private static SearchRepository instance;

    public static SearchRepository getInstance() {
        if (instance == null) {
            instance = new SearchRepository();
        }
        return instance;
    }

    public void requestSearchCollection(String content, ReqCallback<List<Collection>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.searchCollection(token, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

    public void requestSearchProduct(String content, ReqCallback<List<Product>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.searchProduct(token, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new WebException(res.getMessage())))
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }
}

package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.NewsService;
import com.cdtde.chongdetang.entity.Couplet;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/20 21:56
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class CoupletRepository {

    private NewsService service = WebService.create(NewsService.class);

    private static CoupletRepository instance;

    private CoupletRepository() {
    }

    public static CoupletRepository getInstance() {
        if (instance == null) {
            instance = new CoupletRepository();
        }
        return instance;
    }

    public void requestAllCouplet(ReqCallback<List<Couplet>> callback) {
        service.getNews("mryl")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new Exception(res.getMessage())))
                .map(news -> news.stream()
                        .map(Couplet::new)
                        .collect(Collectors.toList()))
                .subscribe(callback.onSuccess,
                        throwable -> callback.onFail.accept(throwable.getMessage()));

    }


}

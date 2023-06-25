package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.NewsService;
import com.cdtde.chongdetang.entity.Info;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/20 22:13
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class InfoRepository {

    private NewsService service = WebService.create(NewsService.class);

    private static InfoRepository instance;

    private InfoRepository() {
    }

    public static InfoRepository getInstance() {
        if (instance == null) {
            instance = new InfoRepository();
        }
        return instance;
    }

    public void requestAllInfo(ReqCallback<List<Info>> callback) {

        service.getNews("hyzx")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new Exception(res.getMessage())))
                .map(news -> news.stream()
                        .map(Info::new)
                        .collect(Collectors.toList()))
                .subscribe(callback.onSuccess, throwable -> callback.onFail.accept(throwable.getMessage()));
    }


}

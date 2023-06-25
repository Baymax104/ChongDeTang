package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.CultureService;
import com.cdtde.chongdetang.entity.Culture;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/20 17:58
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class CultureRepository {

    private CultureService service = WebService.create(CultureService.class);

    private static CultureRepository instance;

    private CultureRepository() {
    }

    public static CultureRepository getInstance() {
        if (instance == null) {
            instance = new CultureRepository();
        }
        return instance;
    }

    public void requestAllCulture(ReqCallback<List<Culture>> callback) {
        service.getAllCulture()
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

}

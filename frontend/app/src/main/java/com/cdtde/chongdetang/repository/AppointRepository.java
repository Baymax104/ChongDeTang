package com.cdtde.chongdetang.repository;

import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.AppointmentService;
import com.cdtde.chongdetang.entity.Appointment;
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
 * @Date 2023/6/20 0:44
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class AppointRepository {

    private AppointmentService service = WebService.create(AppointmentService.class);

    private static AppointRepository instance;

    private AppointRepository() {
    }

    public static AppointRepository getInstance() {
        if (instance == null) {
            instance = new AppointRepository();
        }
        return instance;
    }

    public void requestAllAppointment(ReqCallback<List<Appointment>> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.getAllAppointment(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> res.isSuccess() ?
                        Single.just(res.getData()) :
                        Single.error(new WebException(res.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

    public void requestAddAppointment(Appointment appointment, ReqCallback<Appointment> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        service.addAppointment(token, appointment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> res.isSuccess() ?
                        Single.just(appointment) :
                        Single.error(new WebException(res.getMessage())))
                .doOnSubscribe(disposable -> callback.lifeCycle.onStart())
                .doFinally(callback.lifeCycle::onFinish)
                .subscribe(callback.onSuccess, callback::baseHandleException);
    }

}

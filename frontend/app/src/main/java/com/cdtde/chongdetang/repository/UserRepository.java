package com.cdtde.chongdetang.repository;

import android.net.Uri;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.base.vm.Requester.ReqCallback;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.UserService;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.utils.CameraUtil;
import com.cdtde.chongdetang.utils.ValidateUtil;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/11 1:20
 * @Version 1
 */
@SuppressWarnings("CheckResult")
public class UserRepository {

    private UserService userService = WebService.create(UserService.class);

    private static UserRepository repository;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (repository == null) {
            repository = new UserRepository();
        }
        return repository;
    }

    public void requestFeedbackCommit(String content, ReqCallback<Object> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        userService.addFeedback(token, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> res.isSuccess() ?
                        Single.just(new Object()) :
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

    public void requestLogin(String phone, String password, ReqCallback<Object> callback) {

        String encrypt = ValidateUtil.encrypt(password);
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", encrypt);

        userService.login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> {
                    if (res.isSuccess()) {
                        UserStore.setUser(res.getData());
                        return Single.just(new Object());
                    } else {
                        return Single.error(new WebException(res.getMessage()));
                    }
                })
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

    public void requestUpdateInfo(User newUser, ReqCallback<Object> callback) {
        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        /*
        用户头像的getPhoto有三种情况
        1. 用户没有头像，此时在页面中展示默认头像DEFAULT_PHOTO，但是getPhoto的实际值为null
        2. 用户已经有头像，且此次更新信息未更新头像，此时getPhoto的值为COS中的路径，以.jpg结尾
        3. 用户无论是否有头像，此次更新信息更新了头像，此时getPhoto的值为新头像的URI路径，编码为Base64上传
         */
        if (newUser.getPhoto() != null && !newUser.getPhoto().endsWith(".jpg")) {
            File file = UriUtils.uri2File(Uri.parse(newUser.getPhoto()));
            String base64 = CameraUtil.file2Base64(file);
            newUser.setPhoto(base64);
        }

        userService.updateInfo(token, newUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> {
                    if (res.isSuccess()) {
                        UserStore.setUserInfo(res.getData());
                        return Single.just(new Object());
                    } else {
                        return Single.error(new WebException(res.getMessage()));
                    }
                })
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

    public void requestRegister(String phone, String password,
                                ReqCallback<Object> callback) {
        Map<String, String> map = new HashMap<>();

        String encrypt = ValidateUtil.encrypt(password);
        map.put("phone", phone);
        map.put("password", encrypt);

        userService.register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> res.isSuccess() ?
                        Single.just(new Object()) :
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

    public void requestUpdatePassword(String oldPassword, String newPassword,
                                      ReqCallback<Object> callback) {

        String token = WebService.TOKEN_PREFIX + UserStore.getToken();
        String oldEncrypt = ValidateUtil.encrypt(oldPassword);
        String newEncrypt = ValidateUtil.encrypt(newPassword);

        Map<String, String> map = new HashMap<>();
        map.put("oldPassword", oldEncrypt);
        map.put("newPassword", newEncrypt);

        userService.updatePassword(token, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> {
                    if (res.isSuccess()) {
                        UserStore.setPassword(res.getData());
                        return Single.just(new Object());
                    } else {
                        return Single.error(new WebException(res.getMessage()));
                    }
                })
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

    public void requestUpdatePhone(String phone, ReqCallback<Object> callback) {

        String token = WebService.TOKEN_PREFIX + UserStore.getToken();

        userService.updatePhone(token, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(res -> {
                    if (res.isSuccess()) {
                        UserStore.setPhone(phone);
                        return Single.just(new Object());
                    } else {
                        return Single.error(new WebException(res.getMessage()));
                    }
                })
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

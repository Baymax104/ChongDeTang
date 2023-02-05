package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.UserService;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.util.CameraUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/11 1:20
 * @Version 1
 */
public class MyRepository {

    private UserRepository userRepo;

    private List<Appointment> appointments;

    private List<Address> addresses;

    private UserService userService;

    private static MyRepository repository;

    private MyRepository() {
        userRepo = UserRepository.getInstance();
        userService = WebService.getInstance().create(UserService.class);
        generateTest();
    }

    public static MyRepository getInstance() {
        if (repository == null) {
            repository = new MyRepository();
        }
        return repository;
    }

    public User getUser() {
        return userRepo.getUser();
    }

    public void initUser() {
        userRepo.initUser();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    @SuppressLint("CheckResult")
    public void login(String phone, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);

        Consumer<ResponseResult<User>> onNext = result -> {
            String status = result.getStatus();
            if (status.equals("success")) {
                User user = result.getData();
                if (user != null) {
                    LogUtils.iTag("cdt-web-login", "用户结果返回", user);
                    userRepo.setUser(user);
                    LiveEventBus.get("MyRepository-login", Boolean.class).post(true);
                }
            } else {
                LogUtils.eTag("cdt-web-login",  result.getMessage());
            }
        };
        userService.login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-login", throwable),
                        () -> LogUtils.iTag("cdt-web-login", "登录请求结束")
                );
    }

    @SuppressLint("CheckResult")
    public void update(User user) {
        String token = "Bearer " + user.getToken();

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        if (user.getPhoto() != null) {
            File file = UriUtils.uri2File(Uri.parse(user.getPhoto()));
            String base64 = CameraUtil.file2Base64(file);
            map.put("newPhoto", base64);
        }

        Consumer<ResponseResult<User>> onNext = result -> {
            String status = result.getStatus();
            if (status.equals("success")) {
                User res = result.getData();
                if (res != null) {
                    LogUtils.iTag("cdt-web-updateInfo", "用户结果返回", res);
                    userRepo.setUser(res);
                    LiveEventBus.get("MyRepository-updateInfo", Boolean.class).post(true);
                }
            } else {
                LogUtils.eTag("cdt-web-updateInfo",  result.getMessage());
            }
        };

        userService.updateInfo(token, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-updateInfo", throwable),
                        () -> LogUtils.iTag("cdt-web-updateInfo", "更新请求结束")
                );
    }

    @SuppressLint("CheckResult")
    public void register(String phone, String password) {
        Map<String, String> map = new HashMap<>();
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);

        String encrypt = EncryptUtils.encryptAES2HexString(bytes, AppKey.PASSWORD_KEY, "AES/CBC/PKCS5Padding", AppKey.PASSWORD_IV);

        map.put("phone", phone);
        map.put("password", encrypt);

        Consumer<ResponseResult<Object>> onNext = result -> {
            String status = result.getStatus();
            if (status.equals("success")) {
                LogUtils.iTag("cdt-web-register", "注册成功");
                LiveEventBus.get("MyRepository-register", Boolean.class).post(true);
            } else {
                LogUtils.eTag("cdt-web-register", result.getMessage());
            }
        };

        userService.register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-register", throwable),
                        () -> LogUtils.iTag("cdt-web-register", "注册请求结束")
                );
    }

    private void generateTest() {
        appointments = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            appointments.add(new Appointment());
        }

        addresses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            addresses.add(new Address());
        }
    }

}

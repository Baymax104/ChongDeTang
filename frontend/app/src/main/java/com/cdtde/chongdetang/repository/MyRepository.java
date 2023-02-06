package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.AddressService;
import com.cdtde.chongdetang.dataSource.web.api.UserService;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.util.CameraUtil;
import com.cdtde.chongdetang.util.ValidateUtil;
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
@SuppressLint("CheckResult")
public class MyRepository {

    private UserRepository userRepo;

    private List<Appointment> appointments;

    private List<Address> addresses;

    private UserService userService;
    private AddressService addressService;

    private static MyRepository repository;

    private MyRepository() {
        addresses = new ArrayList<>();
        appointments = new ArrayList<>();
        userRepo = UserRepository.getInstance();
        userService = WebService.getInstance().create(UserService.class);
        addressService = WebService.getInstance().create(AddressService.class);

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

    public void register(String phone, String password) {
        Map<String, String> map = new HashMap<>();

        String encrypt = ValidateUtil.encrypt(password);
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

    public void updatePassword(String oldPassword, String newPassword) {
        String token = "Bearer " + userRepo.getUser().getToken();
        String oldEncrypt = ValidateUtil.encrypt(oldPassword);
        String newEncrypt = ValidateUtil.encrypt(newPassword);

        Map<String, String> map = new HashMap<>();
        map.put("oldPassword", oldEncrypt);
        map.put("newPassword", newEncrypt);

        Consumer<ResponseResult<String>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                String encrypt = result.getData();
                userRepo.getUser().setPassword(encrypt);
                LogUtils.iTag("cdt-web-updatePassword", "密码修改成功");
                LiveEventBus.get("MyRepository-updatePassword", Boolean.class).post(true);
            } else {
                LogUtils.eTag("cdt-web-updatePassword", result.getMessage());
            }
        };

        userService.updatePassword(token, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-updatePassword", throwable),
                        () -> LogUtils.iTag("cdt-web-updatePassword", "修改密码请求结束")
                );
    }

    public void updatePhone(String phone) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                userRepo.getUser().setPhone(phone);
                LogUtils.iTag("cdt-web-updatePhone", "手机号修改成功");
                LiveEventBus.get("MyRepository-updatePhone", Boolean.class).post(true);
            } else {
                LogUtils.eTag("cdt-web-updatePhone", result.getMessage());
            }
        };

        userService.updatePhone(token, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-updatePhone", throwable),
                        () -> LogUtils.iTag("cdt-web-updatePhone", "修改手机号请求结束")
                );
    }

    public void getAllAddress() {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<Address>>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                if (result.getData() != null) {
                    addresses = result.getData();
                    LogUtils.iTag("cdt-web-getAllAddress", "获取地址成功");
                    LiveEventBus.get("MyRepository-getAllAddress", Boolean.class).post(true);
                }
            } else {
                LogUtils.eTag("cdt-web-getAllAddress", result.getMessage());
            }
        };

        addressService.getAllAddress(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-getAllAddress", throwable),
                        () -> LogUtils.iTag("cdt-web-getAllAddress", "获取地址请求结束")
                );
    }

    public void updateAddress(Address address) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                LogUtils.iTag("cdt-web-updateAddress", "修改地址成功");
                LiveEventBus.get("MyRepository-updateAddress", Boolean.class).post(true);
            } else {
                LogUtils.eTag("cdt-web-updateAddress", result.getMessage());
            }
        };

        addressService.updateAddress(token, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-updateAddress", throwable),
                        () -> LogUtils.iTag("cdt-web-updateAddress","修改地址请求结束")
                );
    }

    public void deleteAddress(Address address) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            if (result.getStatus().equals("success")) {
                LogUtils.iTag("cdt-web-deleteAddress", "删除地址成功");
                LiveEventBus.get("MyRepository-deleteAddress", Boolean.class).post(true);
            } else {
                LogUtils.eTag("cdt-web-deleteAddress", result.getMessage());
            }
        };

        addressService.deleteAddress(token, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        throwable -> LogUtils.eTag("cdt-web-deleteAddress", throwable),
                        () -> LogUtils.iTag("cdt-web-deleteAddress", "删除地址请求结束")
                );
    }

    private void generateTest() {
        for (int i = 0; i < 20; i++) {
            appointments.add(new Appointment());
        }
    }

}

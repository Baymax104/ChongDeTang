package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.dataSource.web.WebException;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.AddressService;
import com.cdtde.chongdetang.dataSource.web.api.AppointmentService;
import com.cdtde.chongdetang.dataSource.web.api.UserService;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.util.CameraUtil;
import com.cdtde.chongdetang.util.ValidateUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private List<Collection> collections;

    private List<Product> products;

    private UserService userService;
    private AddressService addressService;
    private AppointmentService appointmentService;

    private static MyRepository repository;

    private MyRepository() {
        addresses = new ArrayList<>();
        appointments = new ArrayList<>();
        collections = new ArrayList<>();
        products = new ArrayList<>();
        userRepo = UserRepository.getInstance();
        userService = WebService.getInstance().create(UserService.class);
        addressService = WebService.getInstance().create(AddressService.class);
        appointmentService = WebService.getInstance().create(AppointmentService.class);
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

    public List<Collection> getCollections() {
        return collections;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void login(String phone, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);

        Consumer<ResponseResult<User>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                User user = result.getData();
                LogUtils.iTag("cdt-web-login", "用户结果返回");
                String base64 = user.getPhoto();
                if (base64 != null) {
                    File file = CameraUtil.base64ToFile(base64);
                    String uri = Uri.fromFile(file).toString();
                    user.setPhoto(uri);
                }
                userRepo.setUser(user);
            } else {
                LogUtils.eTag("cdt-web-login",  result.getMessage());
            }
            LiveEventBus.get("MyRepository-login", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };
        userService.login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("login", "MyRepository-login"),
                        () -> LogUtils.iTag("cdt-web-login", "登录请求结束")
                );
    }

    public void update(User user, String originPhoto) {
        String token = WebService.TOKEN_PREFIX + user.getToken();

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        if (user.getPhoto() != null && !user.getPhoto().equals(originPhoto)) {
            File file = UriUtils.uri2File(Uri.parse(user.getPhoto()));
            String base64 = CameraUtil.file2Base64(file);
            map.put("newPhoto", base64);
        }

        Consumer<ResponseResult<User>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                User res = result.getData();
                LogUtils.iTag("cdt-web-updateInfo", "用户结果返回", res);
                userRepo.setUser(res);
            } else {
                LogUtils.eTag("cdt-web-updateInfo",  result.getMessage());
            }
            LiveEventBus.get("MyRepository-updateInfo", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        userService.updateInfo(token, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("updateInfo", "MyRepository-updateInfo"),
                        () -> LogUtils.iTag("cdt-web-updateInfo", "更新请求结束")
                );
    }

    public void register(String phone, String password) {
        Map<String, String> map = new HashMap<>();

        String encrypt = ValidateUtil.encrypt(password);
        map.put("phone", phone);
        map.put("password", encrypt);

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                LogUtils.iTag("cdt-web-register", "注册成功");
            } else {
                LogUtils.eTag("cdt-web-register", result.getMessage());
            }
            LiveEventBus.get("MyRepository-register", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        userService.register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("register", "MyRepository-register"),
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
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                String encrypt = result.getData();
                userRepo.getUser().setPassword(encrypt);
                LogUtils.iTag("cdt-web-updatePassword", "密码修改成功");
            } else {
                LogUtils.eTag("cdt-web-updatePassword", result.getMessage());
            }
            LiveEventBus.get("MyRepository-updatePassword", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        userService.updatePassword(token, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("updatePassword", "MyRepository-updatePassword"),
                        () -> LogUtils.iTag("cdt-web-updatePassword", "修改密码请求结束")
                );
    }

    public void updatePhone(String phone) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                userRepo.getUser().setPhone(phone);
                LogUtils.iTag("cdt-web-updatePhone", "手机号修改成功");
            } else {
                LogUtils.eTag("cdt-web-updatePhone", result.getMessage());
            }
            LiveEventBus.get("MyRepository-updatePhone", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        userService.updatePhone(token, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("updatePhone", "MyRepository-updatePhone"),
                        () -> LogUtils.iTag("cdt-web-updatePhone", "修改手机号请求结束")
                );
    }

    public void getAllAddress() {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<Address>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                addresses = result.getData();
                LogUtils.iTag("cdt-web-getAllAddress", "获取地址成功");
            } else {
                LogUtils.eTag("cdt-web-getAllAddress", result.getMessage());
            }
            LiveEventBus.get("MyRepository-getAllAddress", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        addressService.getAllAddress(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("getAllAddress", "MyRepository-getAllAddress"),
                        () -> LogUtils.iTag("cdt-web-getAllAddress", "获取地址请求结束")
                );
    }

    public void updateAddress(Address address) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                LogUtils.iTag("cdt-web-updateAddress", "修改地址成功");
            } else {
                LogUtils.eTag("cdt-web-updateAddress", result.getMessage());
            }
            LiveEventBus.get("MyRepository-updateAddress", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        addressService.updateAddress(token, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("updateAddress", "MyRepository-updateAddress"),
                        () -> LogUtils.iTag("cdt-web-updateAddress","修改地址请求结束")
                );
    }

    public void deleteAddress(Address address) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                LogUtils.iTag("cdt-web-deleteAddress", "删除地址成功");
            } else {
                LogUtils.eTag("cdt-web-deleteAddress", result.getMessage());
            }
            LiveEventBus.get("MyRepository-deleteAddress", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        addressService.deleteAddress(token, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("deleteAddress", "MyRepository-deleteAddress"),
                        () -> LogUtils.iTag("cdt-web-deleteAddress", "删除地址请求结束")
                );
    }

    public void getAllAppointment() {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<Appointment>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                appointments = result.getData();
                LogUtils.iTag("cdt-web-getAllAppointment", "获取预约成功");
            } else {
                LogUtils.eTag("cdt-web-getAllAppointment", result.getMessage());
            }
            LiveEventBus.get("MyRepository-getAllAppointment", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        appointmentService.getAllAppointment(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("getAllAppointment", "MyRepository-getAllAppointment"),
                        () -> LogUtils.iTag("cdt-web-getAllAppointment", "获取预约请求结束")
                );
    }

    public void addAppointment(Appointment appointment) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                LogUtils.iTag("cdt-web-addAppointment", "添加预约成功");
            } else {
                LogUtils.eTag("cdt-web-addAppointment", result.getMessage());
            }
            LiveEventBus.get("MyRepository-addAppointment", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        appointmentService.addAppointment(token, appointment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("addAppointment", "MyRepository-addAppointment"),
                        () -> LogUtils.iTag("cdt-web-addAppointment", "添加预约请求结束")
                );
    }

    public void requestUserCollection() {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<UserCollect>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                List<UserCollect> userCollects = result.getData();
                collections = userCollects.stream()
                        .map(UserCollect::getCollection)
                        .collect(Collectors.toList());
                
                LogUtils.iTag("cdt-web-requestUserCollection", "获取收藏藏品成功");
            } else {
                LogUtils.eTag("cdt-web-requestUserCollection", result.getMessage());
            }
            LiveEventBus.get("MyRepository-requestUserCollection", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };
        
        userService.getUserCollect(token, "collection")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("requestUserCollection", "MyRepository-requestUserCollection"),
                        () -> LogUtils.iTag("cdt-web-requestUserCollection", "获取用户收藏藏品请求结束")
                );
    }

    public void requestUserProduct() {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<UserCollect>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                List<UserCollect> userCollects = result.getData();
                products = userCollects.stream()
                        .map(UserCollect::getProduct)
                        .collect(Collectors.toList());

                LogUtils.iTag("cdt-web-requestUserProduct", "获取收藏商品成功");
            } else {
                LogUtils.eTag("cdt-web-requestUserProduct", result.getMessage());
            }
            LiveEventBus.get("MyRepository-requestUserProduct", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        userService.getUserCollect(token, "product")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        WebService.onError("requestUserProduct", "MyRepository-requestUserProduct"),
                        () -> LogUtils.iTag("cdt-web-requestUserProduct", "获取用户收藏商品请求结束")
                );
    }

}

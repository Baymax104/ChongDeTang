package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.blankj.utilcode.util.UriUtils;
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
import com.cdtde.chongdetang.exception.WebException;
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

    public void requestLogin(String phone, String password) {
        // TODO login加密
//        String encrypt = ValidateUtil.encrypt(password);
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);

        Consumer<ResponseResult<User>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                User user = result.getData();
                String base64 = user.getPhoto();
                if (base64 != null) {
                    File file = CameraUtil.base64ToFile(base64);
                    String uri = Uri.fromFile(file).toString();
                    user.setPhoto(uri);
                }
                userRepo.setUser(user);
            }
            LiveEventBus.get("MyRepository-requestLogin", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = e ->
                LiveEventBus.get("MyRepository-requestLogin", WebException.class)
                        .post(new WebException(false, e.getMessage()));

        userService.login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestUpdateInfo(User user, String originPhoto) {
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
                userRepo.setUser(res);
            }
            LiveEventBus.get("MyRepository-requestUpdateInfo", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = e ->
                LiveEventBus.get("MyRepository-requestUpdateInfo", WebException.class)
                        .post(new WebException(false, e.getMessage()));

        userService.updateInfo(token, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestRegister(String phone, String password) {
        Map<String, String> map = new HashMap<>();

        String encrypt = ValidateUtil.encrypt(password);
        map.put("phone", phone);
        map.put("password", encrypt);

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            LiveEventBus.get("MyRepository-requestRegister", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestRegister", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        userService.register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestUpdatePassword(String oldPassword, String newPassword) {
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
            }
            LiveEventBus.get("MyRepository-requestUpdatePassword", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestUpdatePassword", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        userService.updatePassword(token, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestUpdatePhone(String phone) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            if (isSuccess) {
                userRepo.getUser().setPhone(phone);
            }
            LiveEventBus.get("MyRepository-requestUpdatePhone", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestUpdatePhone", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        userService.updatePhone(token, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestAllAddress() {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<Address>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                addresses = result.getData();
            }
            LiveEventBus.get("MyRepository-requestAllAddress", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestAllAddress", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        addressService.getAllAddress(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestUpdateAddress(Address address) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            LiveEventBus.get("MyRepository-requestUpdateAddress", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestUpdateAddress", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        addressService.updateAddress(token, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestDeleteAddress(Address address) {
        String token = "Bearer " + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            LiveEventBus.get("MyRepository-requestDeleteAddress", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestDeleteAddress", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        addressService.deleteAddress(token, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestAllAppointment() {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<List<Appointment>>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success") && result.getData() != null;
            if (isSuccess) {
                appointments = result.getData();
            }
            LiveEventBus.get("MyRepository-requestAllAppointment", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestAllAppointment", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        appointmentService.getAllAppointment(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void requestAddAppointment(Appointment appointment) {
        String token = WebService.TOKEN_PREFIX + userRepo.getUser().getToken();

        Consumer<ResponseResult<Object>> onNext = result -> {
            boolean isSuccess = result.getStatus().equals("success");
            LiveEventBus.get("MyRepository-requestAddAppointment", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestAddAppointment", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        appointmentService.addAppointment(token, appointment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
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
            }
            LiveEventBus.get("MyRepository-requestUserCollection", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestUserCollection", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        userService.getUserCollect(token, "collection")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
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
            }
            LiveEventBus.get("MyRepository-requestUserProduct", WebException.class)
                    .post(new WebException(isSuccess, result.getMessage()));
        };

        Consumer<Throwable> onError = throwable ->
                LiveEventBus.get("MyRepository-requestUserProduct", WebException.class)
                        .post(new WebException(false, throwable.getMessage()));

        userService.getUserCollect(token, "product")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

}

package com.cdtde.chongdetang.repository;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.dataSource.web.WebService;
import com.cdtde.chongdetang.dataSource.web.api.UserService;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.entity.ResponseResult;
import com.cdtde.chongdetang.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/11 1:20
 * @Version 1
 */
public class MyRepository {

    private UserRepository userRepository;

    private List<Appointment> appointments;

    private List<Address> addresses;

    private UserService userService;

    private static MyRepository repository;

    private MyRepository() {
        userRepository = UserRepository.getInstance();
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
        return userRepository.getUser();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void initUser() {
        userRepository.initUser();
    }

    @SuppressLint("CheckResult")
    public void login(String phone, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);

        Consumer<Throwable> onError = LogUtils::e;
        Consumer<ResponseResult<User>> onNext = userResponseResult -> {
            LogUtils.i(userResponseResult);
        };
        userService.login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
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

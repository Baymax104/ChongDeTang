package com.cdtde.chongdetang.repository;

import com.blankj.utilcode.util.Utils;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/11 1:20
 * @Version 1
 */
public class MyRepository {
    private User user;

    private List<Appointment> appointments;

    private List<Address> addresses;

    private static MyRepository repository;

    private MyRepository() {
        // 从全局map中取出用户引用
        AppApplication app = (AppApplication) Utils.getApp();
        Map<String, Object> globalMap = app.getGlobalMap();
        user = (User) globalMap.get("user");

        generateTest();
    }

    public static MyRepository getInstance() {
        if (repository == null) {
            repository = new MyRepository();
        }
        return repository;
    }

    public User getUser() {
        return user;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Address> getAddresses() {
        return addresses;
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

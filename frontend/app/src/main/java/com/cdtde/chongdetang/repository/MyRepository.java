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

    private UserRepository userRepository;

    private List<Appointment> appointments;

    private List<Address> addresses;

    private static MyRepository repository;

    private MyRepository() {
        userRepository = UserRepository.getInstance();
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

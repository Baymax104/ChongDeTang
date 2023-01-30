package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/10 17:52
 * @Version 1
 */
public class MyViewModel extends ViewModel {
    private MyRepository repository;

    private MutableLiveData<User> user;

    public MyViewModel() {
        repository = MyRepository.getInstance();
        user = new MutableLiveData<>(repository.getUser());
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void logout() {
        repository.initUser();
        user.setValue(repository.getUser());
    }
}

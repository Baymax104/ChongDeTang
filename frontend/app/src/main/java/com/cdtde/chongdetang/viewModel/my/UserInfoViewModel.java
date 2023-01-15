package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;

public class UserInfoViewModel extends ViewModel {

    private MyRepository repository;

    public UserInfoViewModel() {
        repository = MyRepository.getInstance();
    }

    public User getUser() {
        return repository.getUser();
    }
}

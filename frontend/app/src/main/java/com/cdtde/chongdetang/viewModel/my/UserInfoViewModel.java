package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;

public class UserInfoViewModel extends ViewModel {

    private MyRepository repository;

    private User user;

    public UserInfoViewModel() {
        repository = MyRepository.getInstance();
        user = repository.getUser();
    }

    public User getUser() {
        return user;
    }


}

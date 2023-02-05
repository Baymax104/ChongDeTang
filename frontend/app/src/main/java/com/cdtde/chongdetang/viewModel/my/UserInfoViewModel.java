package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.LogUtils;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;

public class UserInfoViewModel extends ViewModel {

    private MyRepository repo;

    private User user;

    public UserInfoViewModel() {
        repo = MyRepository.getInstance();
        user = new User(repo.getUser());
    }

    public User getUser() {
        return user;
    }

    public void update() {
        repo.update(user);
    }
}

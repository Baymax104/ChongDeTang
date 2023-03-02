package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;
import com.cdtde.chongdetang.viewModel.MainViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/10 17:52
 * @Version 1
 */
public class MyViewModel extends MainViewModel {
    private MyRepository repo;

    private MutableLiveData<String> userPhoto;

    private MutableLiveData<String> username;

    public MyViewModel() {
        repo = MyRepository.getInstance();
        username = new MutableLiveData<>(repo.getUser().getUsername());
        userPhoto = new MutableLiveData<>(repo.getUser().getPhoto());
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getUserPhoto() {
        return userPhoto;
    }

    public void logout() {
        repo.initUser();
        username.setValue(repo.getUser().getUsername());
        userPhoto.setValue(repo.getUser().getPhoto());
    }

    public void onUserChanged() {
        User user = repo.getUser();
        username.setValue(user.getUsername());
        userPhoto.setValue(user.getPhoto());
    }

    public boolean isLogin() {
        return repo.getUser().getToken() != null;
    }
}

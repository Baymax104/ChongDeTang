package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.ObjectUtils;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.repository.MyRepository;
import com.jeremyliao.liveeventbus.LiveEventBus;

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
        // 若信息未变动，则直接返回
        if (ObjectUtils.equals(user, repo.getUser())) {
            LiveEventBus.get("MyRepository-updateInfo", WebException.class)
                    .post(new WebException(true, null));
            return;
        }
        repo.requestUpdateInfo(user, repo.getUser().getPhoto());
    }
}

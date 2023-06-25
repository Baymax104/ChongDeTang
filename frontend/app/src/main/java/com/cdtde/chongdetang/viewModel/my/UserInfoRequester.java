package com.cdtde.chongdetang.viewModel.my;

import com.blankj.utilcode.util.ObjectUtils;
import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.UserRepository;
import com.cdtde.chongdetang.repository.UserStore;

import io.reactivex.functions.Consumer;

public class UserInfoRequester extends Requester {

    private UserRepository repo = UserRepository.getInstance();


    public void update(User user, Consumer<Object> onSuccess, Consumer<String> onFail) {
        // 若信息未变动，则直接返回
        if (ObjectUtils.equals(user, UserStore.getUser())) {
            try {
                onSuccess.accept(new Object());
            } catch (Exception ignored) {
            }
            return;
        }
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestUpdateInfo(user, UserStore.getPhoto(), callback);
    }
}

package com.cdtde.chongdetang.viewModel.my;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.repository.UserRepository;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/18 22:43
 * @Version 1
 */
public class UserPasswordRequester extends Requester {
    private UserRepository repo = UserRepository.getInstance();

    public void updatePassword(String oldPwd, String newPwd,
                               Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestUpdatePassword(oldPwd, newPwd, callback);
    }
}

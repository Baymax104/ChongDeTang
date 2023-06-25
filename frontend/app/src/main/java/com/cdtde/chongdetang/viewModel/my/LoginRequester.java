package com.cdtde.chongdetang.viewModel.my;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.repository.UserRepository;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/2 0:17
 * @Version 1
 */
public class LoginRequester extends Requester {
    private UserRepository repo = UserRepository.getInstance();

    public void login(String phone, String password,
                      Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestLogin(phone, password, callback);
    }
}

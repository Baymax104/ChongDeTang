package com.cdtde.chongdetang.viewModel.my;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.repository.UserRepository;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/18 23:14
 * @Version 1
 */
public class UserPhoneRequester extends Requester {

    private UserRepository repo = UserRepository.getInstance();

    public void updatePhone(String phone, Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestUpdatePhone(phone, callback);
    }
}

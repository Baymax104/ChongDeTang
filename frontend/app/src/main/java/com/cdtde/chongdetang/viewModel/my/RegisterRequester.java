package com.cdtde.chongdetang.viewModel.my;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.repository.UserRepository;
import com.cdtde.chongdetang.repository.UserStore;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 2:17
 * @Version 1
 */
public class RegisterRequester extends Requester {

    private UserRepository repo = UserRepository.getInstance();

    public void register(Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestRegister(UserStore.getPhone(), UserStore.getPassword(), callback);
    }
}

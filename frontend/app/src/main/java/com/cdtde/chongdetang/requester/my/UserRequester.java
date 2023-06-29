package com.cdtde.chongdetang.requester.my;

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
public class UserRequester extends Requester {

    private UserRepository repo = UserRepository.getInstance();

    public void register(Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestRegister(UserStore.getPhone(), UserStore.getPassword(), callback);
    }

    public void login(String phone, String password,
                      Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestLogin(phone, password, callback);
    }

    public void updateFeedback(String content,
                               Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestFeedbackCommit(content, callback);
    }

}

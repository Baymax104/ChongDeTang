package com.cdtde.chongdetang.viewModel.my;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.repository.UserRepository;

import io.reactivex.functions.Consumer;


public class FeedbackRequester extends Requester {

    private final UserRepository repo = UserRepository.getInstance();

    public void updateFeedback(String content,
                               Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestFeedbackCommit(content, callback);
    }
}

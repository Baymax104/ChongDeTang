package com.cdtde.chongdetang.viewModel.index;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Couplet;
import com.cdtde.chongdetang.repository.CoupletRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 19:06
 * @Version 1
 */
public class CoupletRequester extends Requester {

    private CoupletRepository repository = CoupletRepository.getInstance();

    public void updateAllCouplet(Consumer<List<Couplet>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Couplet>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repository.requestAllCouplet(callback);
    }


}

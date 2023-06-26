package com.cdtde.chongdetang.viewModel.index;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.repository.CultureRepository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.functions.Consumer;

public class CultureRequester extends Requester {

    private CultureRepository repo = CultureRepository.getInstance();

    public void updateAllCulture(String type, Consumer<List<Culture>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Culture>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestAllCulture(type, callback);
    }

}
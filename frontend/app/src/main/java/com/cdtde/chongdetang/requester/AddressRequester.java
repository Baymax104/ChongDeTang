package com.cdtde.chongdetang.requester;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.repository.AddressRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

public class AddressRequester extends Requester {

    private AddressRepository repo = AddressRepository.getInstance();


    public void updateAllAddress(Consumer<List<Address>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Address>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestAllAddress(callback);
    }

    public void updateAddress(Address address,
                              Consumer<Address> onSuccess, Consumer<String> onFail) {
        ReqCallback<Address> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestUpdateAddress(address, callback);
    }

    public void removeAddress(Address address,
                              Consumer<Address> onSuccess, Consumer<String> onFail) {
        ReqCallback<Address> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestRemoveAddress(address, callback);
    }
}

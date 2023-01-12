package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.repository.MyRepository;

import java.util.ArrayList;
import java.util.List;

public class AddressViewModel extends ViewModel {

    private MyRepository repository;

    private MutableLiveData<List<Address>> addresses;

    public AddressViewModel() {
        repository = MyRepository.getInstance();
        addresses = new MutableLiveData<>(repository.getAddresses());
    }

    public MutableLiveData<List<Address>> getAddresses() {
        return addresses;
    }
}

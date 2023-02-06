package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.repository.MyRepository;

import java.util.List;

public class AddressViewModel extends ViewModel {

    private MyRepository repo;

    private MutableLiveData<List<Address>> addresses;

    public AddressViewModel() {
        repo = MyRepository.getInstance();
        addresses = new MutableLiveData<>();
        updateAllAddress();
    }

    public MutableLiveData<List<Address>> getAddresses() {
        return addresses;
    }

    public void refreshAllAddress() {
        addresses.setValue(repo.getAddresses());
    }

    public void updateAllAddress() {
        repo.getAllAddress();
    }
}

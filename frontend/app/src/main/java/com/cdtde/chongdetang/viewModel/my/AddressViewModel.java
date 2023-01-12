package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressViewModel extends ViewModel {
    private List<Address> dataList;

    public AddressViewModel() {
        dataList = new ArrayList<>();
        //设置假数据
        for (int i=0;i<5;i++){
            Address tmp =new Address();
            dataList.add(tmp);
        }
    }

    public List<Address> getDataList() {
        return dataList;
    }
}

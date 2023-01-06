package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentViewModel extends ViewModel {
    private List<Appointment> dataList;

    public AppointmentViewModel() {
        dataList=new ArrayList<>();
        //设置假数据
        for (int i=0;i<5;i++){
            Appointment tmp =new Appointment();
            dataList.add(tmp);
        }
    }

    public List<Appointment> getDataList() {
        return dataList;
    }
}

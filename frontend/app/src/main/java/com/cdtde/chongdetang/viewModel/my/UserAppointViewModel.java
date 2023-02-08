package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.repository.MyRepository;

import java.util.List;

public class UserAppointViewModel extends ViewModel {

    private MyRepository repository;
    private MutableLiveData<List<Appointment>> appointments;

    public UserAppointViewModel() {
        repository = MyRepository.getInstance();
        appointments = new MutableLiveData<>(repository.getAppointments());
    }

    public MutableLiveData<List<Appointment>> getAppointments() {
        return appointments;
    }

}

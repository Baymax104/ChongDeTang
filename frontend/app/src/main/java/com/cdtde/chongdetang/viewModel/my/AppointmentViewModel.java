package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.repository.MyRepository;

import java.util.ArrayList;
import java.util.List;

public class AppointmentViewModel extends ViewModel {

    private MyRepository repository;
    private MutableLiveData<List<Appointment>> appointments;

    public AppointmentViewModel() {
        repository = MyRepository.getInstance();
        appointments = new MutableLiveData<>(repository.getAppointments());
    }

    public MutableLiveData<List<Appointment>> getAppointments() {
        return appointments;
    }

}

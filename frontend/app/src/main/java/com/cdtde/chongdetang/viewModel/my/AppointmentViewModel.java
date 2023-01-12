package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentViewModel extends ViewModel {
    private MutableLiveData<List<Appointment>> appointments;

    public AppointmentViewModel() {
        appointments = new MutableLiveData<>();

        generateTest();
    }

    public MutableLiveData<List<Appointment>> getAppointments() {
        return appointments;
    }

    private void generateTest() {
        List<Appointment> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new Appointment());
        }
        appointments.setValue(data);
    }
}

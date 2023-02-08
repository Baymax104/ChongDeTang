package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.repository.MyRepository;

import java.util.List;

public class AppointViewModel extends ViewModel {

    private MyRepository repo;
    private MutableLiveData<List<Appointment>> appointments;

    public AppointViewModel() {
        repo = MyRepository.getInstance();
        appointments = new MutableLiveData<>();
        updateAllAppointment();
    }

    public MutableLiveData<List<Appointment>> getAppointments() {
        return appointments;
    }

    public boolean isLogin() {
        return repo.getUser().getToken() != null;
    }

    public void updateAllAppointment() {
        repo.getAllAppointment();
    }

    public void refreshAllAppointment() {
        appointments.setValue(repo.getAppointments());
    }

    public void addAppointment(Appointment appointment) {
        repo.addAppointment(appointment);
    }
}

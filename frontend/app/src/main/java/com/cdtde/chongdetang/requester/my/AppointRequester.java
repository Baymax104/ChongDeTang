package com.cdtde.chongdetang.requester.my;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.repository.AppointRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

public class AppointRequester extends Requester {

    private AppointRepository repo = AppointRepository.getInstance();

    public void updateAllAppointment(Consumer<List<Appointment>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Appointment>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestAllAppointment(callback);
    }


    public void addAppointment(Appointment appointment,
                               Consumer<Appointment> onSuccess, Consumer<String> onFail) {
        ReqCallback<Appointment> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestAddAppointment(appointment, callback);
    }
}

package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemAppointmentBinding;
import com.cdtde.chongdetang.entity.Appointment;


public class AppointmentAdapter extends BaseAdapter<Appointment, ItemAppointmentBinding> {

    public AppointmentAdapter() {
        super(R.layout.item_appointment);
    }

    @Override
    protected void onBind(ItemAppointmentBinding binding, Appointment item) {
        binding.setAppointment(item);
    }

}

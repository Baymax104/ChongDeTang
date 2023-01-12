package com.cdtde.chongdetang.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemAppointmentBinding;
import com.cdtde.chongdetang.entity.Appointment;


public class AppointmentAdapter extends BaseAdapter<Appointment> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ItemAppointmentBinding binding = ItemAppointmentBinding.bind(holder.itemView);
        binding.setAppointment(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends BaseViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

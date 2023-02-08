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
        Appointment appointment = data.get(position);
        ((ViewHolder) holder).binding.setAppointment(appointment);
        ((ViewHolder) holder).binding.detailEntry.setOnClickListener(v -> onItemClickListener.onClick(appointment));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    private static class ViewHolder extends BaseViewHolder {

        ItemAppointmentBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAppointmentBinding.bind(itemView);
        }
    }
}

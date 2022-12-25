package com.cdtde.chongdetang.view.my.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdtde.chongdetang.databinding.ItemAponintmentBinding;
import com.cdtde.chongdetang.model.Appointment;
import com.cdtde.chongdetang.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.viewHolder> {
    // 数据
    private List<Appointment> appointList;
    // 构造函数
    public AppointmentAdapter(List<Appointment> appointList) {
        this.appointList = appointList;
    }

    @NonNull
    @Override
//    这个方法返回viewholder，创建一个viewholder
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 关联相关样式
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aponintment,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Appointment tmp=appointList.get(position);
        holder.binding.appointmentDate.setText(tmp.getDate());
        holder.binding.appointmentId.setText(tmp.getOrderID());
        holder.binding.appointmentTime.setText(tmp.getOrderTime());
        holder.binding.appointmentMoney.setText(tmp.getOrderMoney());
    }

    @Override
    public int getItemCount() {
        return appointList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        ItemAponintmentBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAponintmentBinding.bind(itemView);
        }
    }
}

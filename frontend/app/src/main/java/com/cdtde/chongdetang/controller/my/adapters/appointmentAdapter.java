package com.cdtde.chongdetang.controller.my.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdtde.chongdetang.ItemClass.appointment;
import com.cdtde.chongdetang.R;

import java.util.List;

public class appointmentAdapter extends RecyclerView.Adapter<appointmentAdapter.viewHolder> {
    // 数据
    private List<appointment> appointList;
    // 上下文环境
    private Context context;
    // 构造函数
    public appointmentAdapter(List<appointment> appointList, Context context) {
        this.appointList = appointList;
        this.context = context;
    }

    @NonNull
    @Override
//    这个方法返回viewholder，创建一个viewholder
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 关联相关样式
        View v = LayoutInflater.from(context).inflate(R.layout.layout_aponintment_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        appointment tmp=appointList.get(position);
        holder.date.setText(tmp.getDate());
        holder.orderID.setText(tmp.getOrderID());
        holder.orderTime.setText(tmp.getOrderTime());
        holder.orderMoney.setText(tmp.getOrderMoney());

    }

    @Override
    public int getItemCount() {
        return appointList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView date,orderID,orderTime,orderMoney;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.myAppointment_date_tv);
            orderID=itemView.findViewById(R.id.myAppointment_ID_tv);
            orderTime=itemView.findViewById(R.id.myAppointment_orderTime_tv);
            orderMoney=itemView.findViewById(R.id.myAppointment_orderMoney_tv);
        }
    }
}

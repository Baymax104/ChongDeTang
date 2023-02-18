package com.cdtde.chongdetang.util.adapter;

import android.view.View;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/8 15:54
 * @Version 1
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected List<T> data;

    public interface OnItemClickListener<T> {
        void onClick(T data);
    }

    protected OnItemClickListener<T> onItemClickListener = data1 -> {
    };

    public BaseAdapter() {
    }

    public BaseAdapter(List<T> data) {
        this.data = data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

package com.cdtde.chongdetang.util.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/8 15:54
 * @Version 1
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected List<T> data;

    public BaseAdapter() {
    }

    public BaseAdapter(List<T> data) {
        this.data = data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

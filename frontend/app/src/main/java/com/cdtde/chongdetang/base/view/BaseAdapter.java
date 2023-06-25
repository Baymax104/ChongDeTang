package com.cdtde.chongdetang.base.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/8 15:54
 * @Version 1
 */
public abstract class BaseAdapter<E, B extends ViewDataBinding>
        extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected List<E> list = new ArrayList<>();

    @LayoutRes
    protected Integer layout;

    protected ListHandlerFactory factory;


    public BaseAdapter(@NonNull Integer layout) {
        this.layout = layout;
    }

    public void setList(@NonNull List<E> list) {
        this.list = list;
    }

    public void setFactory(ListHandlerFactory factory) {
        this.factory = factory;
    }

    protected abstract void onBind(B binding, E item);

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layout, parent, false);
        return new BaseViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        int pos = holder.getAbsoluteAdapterPosition();
        E element = list.get(pos);
        if (binding != null && element != null) {
            onBind(binding, element);
            if (factory != null) {
                BindingConfig config = factory.getBindingConfig();
                for (int i = 0; i < config.params.size(); i++) {
                    int key = config.params.keyAt(i);
                    Object value = config.params.get(key);
                    binding.setVariable(key, value);
                }
            }
            binding.executePendingBindings();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public abstract static class ListHandlerFactory {

        public interface OnItemClickListener<T> {
            void onClick(T data, View view);
        }

        public abstract BindingConfig getBindingConfig();
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

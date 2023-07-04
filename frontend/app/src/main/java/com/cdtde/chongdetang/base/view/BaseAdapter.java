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
 * RecyclerView 适配器父类，封装了子项的数据绑定以及事件绑定.
 * 利用绑定参数配置的思想，将列表子项的数据实体和事件处理作为绑定参数，统一绑定到视图中.
 * 其中，{@link ListHandlerFactory} 子类重写的 {@link ListHandlerFactory#getBindingConfig()} 返回
 * 子项的事件处理对象参数，调用 {@link #setFactory(ListHandlerFactory)} 将重写类对象传入，即可将事件绑定到子项中.
 * 数据实体则由适配器子类在重写的 {@link #onBind(ViewDataBinding, Object)} 方法中绑定
 * @param <E> 列表数据实体类型
 * @param <B> DataBinding 类型
 * @author John
 */
public abstract class BaseAdapter<E, B extends ViewDataBinding>
        extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected List<E> list = new ArrayList<>();

    @LayoutRes
    protected Integer layout;

    protected ListHandlerFactory factory;


    /**
     * BaseAdapter构造器
     * @param layout 子项布局ID
     */
    public BaseAdapter(@NonNull Integer layout) {
        this.layout = layout;
    }

    /**
     * 设置展示列表
     * @param list 列表对象
     */
    public void setList(@NonNull List<E> list) {
        this.list = list;
    }

    /**
     * 设置子项事件处理器工厂
     * @param factory {@link ListHandlerFactory}子类
     */
    public void setFactory(ListHandlerFactory factory) {
        this.factory = factory;
    }

    /**
     * 子类重写该方法，将 item 绑定到 binding 对象中.
     * @param binding 子项布局的 ViewDataBinding 对象
     * @param item 子项数据实体对象
     */
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

    /**
     * 适配器事件处理对象工厂.
     * 其中包含 {@link OnItemClickListener} 接口，通常使用该接口的实现对象进行事件定义.
     */
    public abstract static class ListHandlerFactory {

        /**
         * 事件监听接口，data 参数为子项的数据对象，view 参数为子项布局 View 对象.
         * 在视图绑定中使用 Lambda 表达式传入 data 参数，例如 onClick="@{v->listener.onClick(data, v)}".
         * @param <T> 子项数据对象类型
         */
        public interface OnItemClickListener<T> {
            void onClick(T data, View view);
        }

        /**
         * 由子类重写该方法，设置参数映射，返回 BindingConfig 对象.
         * @return {@link BindingConfig}
         */
        public abstract BindingConfig getBindingConfig();
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

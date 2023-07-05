package com.cdtde.chongdetang.adapter.recycler;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemOrderBinding;
import com.cdtde.chongdetang.entity.Order;

/**
 * @ClassName UserOrderAdapter
 * @Author John
 * @Date 2023/7/5 16:19
 * @Version 1.0
 */
public class UserOrderAdapter extends BaseAdapter<Order, ItemOrderBinding> {


    public UserOrderAdapter() {
        super(R.layout.item_order);
    }

    @Override
    protected void onBind(ItemOrderBinding binding, Order item) {
        binding.setOrder(item);
    }
}

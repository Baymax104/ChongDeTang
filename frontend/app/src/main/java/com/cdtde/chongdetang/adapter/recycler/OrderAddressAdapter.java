package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemOrderAddressBinding;
import com.cdtde.chongdetang.entity.Address;

/**
 * @ClassName OrderAddressAdapter
 * @Author John
 * @Date 2023/7/4 10:39
 * @Version 1.0
 */
public class OrderAddressAdapter extends BaseAdapter<Address, ItemOrderAddressBinding> {

    public OrderAddressAdapter() {
        super(R.layout.item_order_address);
    }

    @Override
    protected void onBind(ItemOrderAddressBinding binding, Address item) {
        binding.setAddress(item);
    }
}

package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemAddressBinding;
import com.cdtde.chongdetang.entity.Address;

public class AddressAdapter extends BaseAdapter<Address, ItemAddressBinding> {

    public AddressAdapter() {
        super(R.layout.item_address);
    }

    @Override
    protected void onBind(ItemAddressBinding binding, Address item) {
        binding.setAddress(item);
    }
}

package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemCoupletBinding;
import com.cdtde.chongdetang.entity.Couplet;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 19:15
 * @Version 1
 */
public class CoupletAdapter extends BaseAdapter<Couplet, ItemCoupletBinding> {

    public CoupletAdapter() {
        super(R.layout.item_couplet);
    }

    @Override
    protected void onBind(ItemCoupletBinding binding, Couplet item) {
        binding.setCouplet(item);
    }

}

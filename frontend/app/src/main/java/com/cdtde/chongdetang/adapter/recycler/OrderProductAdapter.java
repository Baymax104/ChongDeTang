package com.cdtde.chongdetang.adapter.recycler;


import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemOrderProductBinding;
import com.cdtde.chongdetang.entity.Shopping;

/**
 * @ClassName OrderProductAdapter
 * @Author John
 * @Date 2023/7/3 13:53
 * @Version 1.0
 */
public class OrderProductAdapter extends BaseAdapter<Shopping, ItemOrderProductBinding> {

    public OrderProductAdapter() {
        super(R.layout.item_order_product);
    }

    @Override
    protected void onBind(ItemOrderProductBinding binding, Shopping item) {
        binding.setShopping(item);
    }
}

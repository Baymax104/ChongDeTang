package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemShoppingProductBinding;
import com.cdtde.chongdetang.entity.CheckedShopping;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/20 16:35
 * @Version 1
 */
public class ShoppingAdapter extends BaseAdapter<CheckedShopping, ItemShoppingProductBinding> {

    public ShoppingAdapter() {
        super(R.layout.item_shop_product);
    }

    @Override
    protected void onBind(ItemShoppingProductBinding binding, CheckedShopping item) {
        binding.setCheckedShopping(item);
    }
}

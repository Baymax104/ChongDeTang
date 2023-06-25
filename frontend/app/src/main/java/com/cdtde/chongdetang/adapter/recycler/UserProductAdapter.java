package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemUserProductBinding;
import com.cdtde.chongdetang.entity.Product;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/19 0:44
 * @Version 1
 */
public class UserProductAdapter extends BaseAdapter<Product, ItemUserProductBinding> {

    public UserProductAdapter() {
        super(R.layout.item_user_product);
    }

    @Override
    protected void onBind(ItemUserProductBinding binding, Product item) {
        binding.setProduct(item);
    }
}

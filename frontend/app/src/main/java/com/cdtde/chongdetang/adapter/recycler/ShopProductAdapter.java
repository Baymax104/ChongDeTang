package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemShopProductBinding;
import com.cdtde.chongdetang.entity.Product;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/8 20:51
 * @Version 1
 */
public class ShopProductAdapter extends BaseAdapter<Product, ItemShopProductBinding> {

    public ShopProductAdapter() {
        super(R.layout.item_shop_product);
    }

    @Override
    protected void onBind(ItemShopProductBinding binding, Product item) {
        binding.setProduct(item);
    }

}

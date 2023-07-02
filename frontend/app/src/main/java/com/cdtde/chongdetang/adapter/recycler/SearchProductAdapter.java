package com.cdtde.chongdetang.adapter.recycler;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemSearchProductBinding;
import com.cdtde.chongdetang.entity.Product;

/**
 * @ClassName SearchProductAdapter
 * @Author John
 * @Date 2023/7/2 17:24
 * @Version 1.0
 */
public class SearchProductAdapter extends BaseAdapter<Product, ItemSearchProductBinding> {

    public SearchProductAdapter() {
        super(R.layout.item_search_product);
    }

    @Override
    protected void onBind(ItemSearchProductBinding binding, Product item) {
        binding.setProduct(item);
    }
}

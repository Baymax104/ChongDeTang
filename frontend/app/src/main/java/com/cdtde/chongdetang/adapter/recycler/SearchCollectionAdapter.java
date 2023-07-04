package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemSearchCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;

/**
 * @ClassName SearchCollectionAdapter
 * @Author John
 * @Date 2023/7/2 17:22
 * @Version 1.0
 */
public class SearchCollectionAdapter extends BaseAdapter<Collection, ItemSearchCollectionBinding> {

    public SearchCollectionAdapter() {
        super(R.layout.item_search_collection);
    }

    @Override
    protected void onBind(ItemSearchCollectionBinding binding, Collection item) {
        binding.setCollection(item);
    }
}

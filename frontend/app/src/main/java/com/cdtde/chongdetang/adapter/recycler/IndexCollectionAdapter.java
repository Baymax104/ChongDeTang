package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemIndexCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 16:02
 * @Version 1
 */
public class IndexCollectionAdapter extends BaseAdapter<Collection, ItemIndexCollectionBinding> {

    public IndexCollectionAdapter() {
        super(R.layout.item_index_collection);
    }

    @Override
    protected void onBind(ItemIndexCollectionBinding binding, Collection item) {
        binding.setCollection(item);
    }
}

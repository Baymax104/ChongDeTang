package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemUserCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/19 0:41
 * @Version 1
 */
public class UserCollectionAdapter extends BaseAdapter<Collection, ItemUserCollectionBinding> {

    public UserCollectionAdapter() {
        super(R.layout.item_user_collection);
    }

    @Override
    protected void onBind(ItemUserCollectionBinding binding, Collection item) {
        binding.setCollection(item);
    }
}

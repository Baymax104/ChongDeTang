package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemExhibitCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 23:13
 * @Version 1
 */
public class ExhibitCollectionAdapter extends BaseAdapter<Collection, ItemExhibitCollectionBinding> {

    public ExhibitCollectionAdapter() {
        super(R.layout.item_exhibit_collection);
    }

    @Override
    protected void onBind(ItemExhibitCollectionBinding binding, Collection item) {
        binding.setCollection(item);
    }

}

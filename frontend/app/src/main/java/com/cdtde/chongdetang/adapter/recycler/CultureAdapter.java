package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemCultureBinding;
import com.cdtde.chongdetang.entity.Culture;


public class CultureAdapter extends BaseAdapter<Culture, ItemCultureBinding> {

    public CultureAdapter() {
        super(R.layout.item_culture);
    }

    @Override
    protected void onBind(ItemCultureBinding binding, Culture item) {
        binding.setCulture(item);
    }

}

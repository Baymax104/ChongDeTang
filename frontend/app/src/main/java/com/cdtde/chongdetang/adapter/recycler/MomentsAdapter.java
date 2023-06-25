package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemMomentBinding;
import com.cdtde.chongdetang.entity.Moment;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 21:06
 * @Version 1
 */
public class MomentsAdapter extends BaseAdapter<Moment, ItemMomentBinding> {

    public MomentsAdapter() {
        super(R.layout.item_moment);
    }

    @Override
    protected void onBind(ItemMomentBinding binding, Moment item) {
        binding.setMoment(item);
    }

}

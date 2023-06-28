package com.cdtde.chongdetang.adapter.recycler;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.databinding.ItemInfoBinding;
import com.cdtde.chongdetang.entity.Info;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 21:06
 * @Version 1
 */
public class InfosAdapter extends BaseAdapter<Info, ItemInfoBinding> {

    public InfosAdapter() {
        super(R.layout.item_info);
    }

    @Override
    protected void onBind(ItemInfoBinding binding, Info item) {
        binding.setInfo(item);
    }
}

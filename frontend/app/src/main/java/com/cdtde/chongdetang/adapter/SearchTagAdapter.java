package com.cdtde.chongdetang.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemSearchTagBinding;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/24 22:24
 * @Version 1
 */
public class SearchTagAdapter extends TagAdapter<String> {

    public SearchTagAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout flowLayout, int i, String s) {
        View view = LayoutInflater.from(flowLayout.getContext()).inflate(R.layout.item_search_tag, flowLayout, false);
        ItemSearchTagBinding binding = ItemSearchTagBinding.bind(view);
        binding.setContent(s);
        return view;
    }

}

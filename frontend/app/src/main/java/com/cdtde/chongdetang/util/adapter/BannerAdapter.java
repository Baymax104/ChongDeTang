package com.cdtde.chongdetang.util.adapter;

import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/8 15:03
 * @Version 1
 */
public class BannerAdapter extends BannerImageAdapter<Integer> {

    public BannerAdapter(List<Integer> mData) {
        super(mData);
    }

    @Override
    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
        holder.imageView.setImageResource(data);
    }
}

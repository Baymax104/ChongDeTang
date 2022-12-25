package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.R;
import com.stx.xhb.androidx.entity.LocalImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/25 23:12
 * @Version 1
 */
public class IndexViewModel extends ViewModel {
    private List<LocalImageInfo> bannerImg;

    public IndexViewModel() {
        bannerImg = new ArrayList<>();
        bannerImg.add(new LocalImageInfo(R.drawable.test_picture));
        bannerImg.add(new LocalImageInfo(R.drawable.test_picture));
        bannerImg.add(new LocalImageInfo(R.drawable.test_picture));
    }

    public List<LocalImageInfo> getBannerImg() {
        return bannerImg;
    }
}

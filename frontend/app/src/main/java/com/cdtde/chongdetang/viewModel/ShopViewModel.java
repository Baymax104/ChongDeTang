package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/7 15:49
 * @Version 1
 */
public class ShopViewModel extends ViewModel {

    private MutableLiveData<List<Integer>> bannerImg;

    public ShopViewModel() {

        bannerImg = new MutableLiveData<>();
        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.shop_banner1);
        imgs.add(R.drawable.shop_banner2);
        imgs.add(R.drawable.shop_banner3);
        bannerImg.setValue(imgs);
    }

    public MutableLiveData<List<Integer>> getBannerImg() {
        return bannerImg;
    }

    private void generateTest() {
    }
}

package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.R;
import com.stx.xhb.androidx.entity.LocalImageInfo;

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
    private MutableLiveData<List<LocalImageInfo>> bannerImg;

    public ShopViewModel() {
        bannerImg = new MutableLiveData<>(new ArrayList<>());

        generateTest();
    }

    private void generateTest() {
        List<LocalImageInfo> value1 = bannerImg.getValue();
        for (int i = 0; i < 3; i++) {
            if (value1 != null) {
                value1.add(new LocalImageInfo(R.drawable.test_picture));
            }
        }
        bannerImg.setValue(value1);

    }
}

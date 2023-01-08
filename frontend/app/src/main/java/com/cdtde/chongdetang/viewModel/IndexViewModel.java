package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.model.Collection;

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
    private MutableLiveData<List<Integer>> bannerImg;

    private MutableLiveData<List<Collection>> collections;


    public IndexViewModel() {
        bannerImg = new MutableLiveData<>();
        collections = new MutableLiveData<>();

        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.index_banner1);
        imgs.add(R.drawable.index_banner2);
        imgs.add(R.drawable.index_banner3);
        bannerImg.setValue(imgs);

        generateTest();
    }

    public MutableLiveData<List<Integer>> getBannerImg() {
        return bannerImg;
    }

    public MutableLiveData<List<Collection>> getCollections() {
        return collections;
    }



    private void generateTest() {
        List<Collection> value2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            value2.add(new Collection());
        }
        collections.setValue(value2);
    }
}

package com.cdtde.chongdetang.viewModel;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.model.Collection;
import com.cdtde.chongdetang.util.IndexCollectionAdapter;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.LocalImageInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/25 23:12
 * @Version 1
 */
public class IndexViewModel extends ViewModel {
    private MutableLiveData<List<LocalImageInfo>> bannerImg;

    private MutableLiveData<List<Collection>> collections;


    public IndexViewModel() {
        bannerImg = new MutableLiveData<>(new ArrayList<>());
        collections = new MutableLiveData<>(new ArrayList<>());

        generateTest();
    }

    public MutableLiveData<List<LocalImageInfo>> getBannerImg() {
        return bannerImg;
    }

    public MutableLiveData<List<Collection>> getCollections() {
        return collections;
    }


    @BindingAdapter("bannerData")
    public static void setBannerData(XBanner banner, List<LocalImageInfo> data) {
        banner.setBannerData(data);
    }

    @BindingAdapter({"recyclerAdapter", "recyclerData"})
    public static void setIndexRecyclerView(RecyclerView view, IndexCollectionAdapter adapter, List<Collection> data) {
        adapter.setData(data);
        view.setAdapter(adapter);
    }

    private void generateTest() {
        List<LocalImageInfo> value1 = bannerImg.getValue();
        for (int i = 0; i < 3; i++) {
            if (value1 != null) {
                value1.add(new LocalImageInfo(R.drawable.test_picture));
            }
        }
        bannerImg.setValue(value1);
        List<Collection> value2 = collections.getValue();
        for (int i = 0; i < 4; i++) {
            if (value2 != null) {
                value2.add(new Collection());
            }
        }
        collections.setValue(value2);
    }
}

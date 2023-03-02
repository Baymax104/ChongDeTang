package com.cdtde.chongdetang.viewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.view.exhibit.ExhibitFragment;
import com.cdtde.chongdetang.view.index.IndexFragment;
import com.cdtde.chongdetang.view.my.MyFragment;
import com.cdtde.chongdetang.view.shop.ShopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/3/1 23:47
 * @Version 1
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<Integer> page;

    private List<Fragment> fragments;

    public MainViewModel() {
        page = new MutableLiveData<>(0);
        fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new ExhibitFragment());
        fragments.add(new ShopFragment());
        fragments.add(new MyFragment());
    }

    public MutableLiveData<Integer> getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page.setValue(page);
    }

    public List<Fragment> getFragments() {
        return fragments;
    }
}

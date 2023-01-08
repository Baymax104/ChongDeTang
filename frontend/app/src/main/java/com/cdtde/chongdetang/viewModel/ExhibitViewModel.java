package com.cdtde.chongdetang.viewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.model.Collection;
import com.cdtde.chongdetang.view.exhibit.TabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 22:32
 * @Version 1
 */
public class ExhibitViewModel extends ViewModel {

    private MutableLiveData<List<Fragment>> tabFragments;

    public ExhibitViewModel() {
        tabFragments = new MutableLiveData<>(new ArrayList<>());

        // 测试数据
        generateTest();
    }


    public MutableLiveData<List<Fragment>> getTabFragments() {
        return tabFragments;
    }

    public void addData(List<Collection> data) {
        List<Fragment> value = tabFragments.getValue();
        if (value != null) {
            value.add(new TabFragment(data));
            tabFragments.setValue(value);
        }
    }


    private void generateTest() {
        List<Collection> data1 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data1.add(new Collection());
        }
        addData(data1);

        List<Collection> data2 = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data2.add(new Collection());
        }
        addData(data2);

        List<Collection> data3 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data3.add(new Collection());
        }
        addData(data3);
    }
}

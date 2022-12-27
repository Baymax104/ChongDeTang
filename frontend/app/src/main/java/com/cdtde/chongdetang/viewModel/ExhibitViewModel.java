package com.cdtde.chongdetang.viewModel;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.widget.ViewPager2;

import com.cdtde.chongdetang.model.Collection;
import com.cdtde.chongdetang.model.ExhibitTab;
import com.cdtde.chongdetang.util.FragmentAdapter;
import com.cdtde.chongdetang.view.exhibit.TabFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

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
    private MutableLiveData<ArrayList<CustomTabEntity>> tabs;

    private MutableLiveData<List<Fragment>> tabFragments;

    public ExhibitViewModel() {
        tabs = new MutableLiveData<>();
        tabFragments = new MutableLiveData<>(new ArrayList<>());

        ArrayList<CustomTabEntity> tabTitle = new ArrayList<>();
        tabTitle.add(new ExhibitTab("书法·系列"));
        tabTitle.add(new ExhibitTab("篆刻·系列"));
        tabTitle.add(new ExhibitTab("牌匾·系列"));
        tabs.setValue(tabTitle);

        // 测试数据
        generateTest();
    }

    public MutableLiveData<ArrayList<CustomTabEntity>> getTabs() {
        return tabs;
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

    @BindingAdapter("tabData")
    public static void setTabData(CommonTabLayout layout, ArrayList<CustomTabEntity> data) {
        layout.setTabData(data);
    }

    @BindingAdapter({"fragmentAdapter", "fragments"})
    public static void setFragments(ViewPager2 viewPager, FragmentAdapter adapter, List<Fragment> data) {
        adapter.setFragments(data);
        viewPager.setAdapter(adapter);
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

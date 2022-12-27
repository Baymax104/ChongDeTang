package com.cdtde.chongdetang.viewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.model.Collection;
import com.cdtde.chongdetang.model.ExhibitTab;
import com.cdtde.chongdetang.view.exhibit.TabFragment;
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
    private ArrayList<CustomTabEntity> tabs;

    private ArrayList<Fragment> tabFragments;

    public ExhibitViewModel() {
        tabs = new ArrayList<>();
        tabFragments = new ArrayList<>();
        // 测试数据
        generateTest();
    }

    public ArrayList<CustomTabEntity> getTabs() {
        return tabs;
    }

    public ArrayList<Fragment> getTabFragments() {
        return tabFragments;
    }

    public void addTab(String title, List<Collection> data) {
        tabs.add(new ExhibitTab(title));
        tabFragments.add(new TabFragment(data));
    }

    private void generateTest() {
        List<Collection> data1 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data1.add(new Collection());
        }
        List<Collection> data2 = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data2.add(new Collection());
        }
        List<Collection> data3 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data3.add(new Collection());
        }
        addTab("书法·系列", data1);
        addTab("篆刻·系列", data2);
        addTab("牌匾·系列", data3);
    }
}

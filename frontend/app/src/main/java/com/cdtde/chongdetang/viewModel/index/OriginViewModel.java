package com.cdtde.chongdetang.viewModel.index;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.repository.IndexRepository;
import com.cdtde.chongdetang.view.index.origin.OriginFragment;

import java.util.ArrayList;
import java.util.List;

public class OriginViewModel extends ViewModel {
    private IndexRepository repo;

    private List<Fragment> fragments;
    private List<String> urls;

    public OriginViewModel(){
        repo = IndexRepository.getInstance();

        urls = new ArrayList<>();
        urls.add("http://www.cdtde.com/m/list.php?tid=12"); //走进崇德堂
        urls.add("http://www.cdtde.com/m/list.php?tid=13"); //崇德缘起
        urls.add("http://www.cdtde.com/m/list.php?tid=14"); //馆长介绍

        fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(OriginFragment.newInstance(i + 1));
        }
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public String getPageUrl(int page) {
        return urls.get(page - 1);
    }
}
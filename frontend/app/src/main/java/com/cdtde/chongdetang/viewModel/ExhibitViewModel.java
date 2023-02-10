package com.cdtde.chongdetang.viewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.repository.ExhibitRepository;
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

    private ExhibitRepository repo;
    private MutableLiveData<List<Fragment>> tabFragments;
    private MutableLiveData<List<Collection>> collectionPage1;
    private MutableLiveData<List<Collection>> collectionPage2;
    private MutableLiveData<List<Collection>> collectionPage3;

    public ExhibitViewModel() {
        repo = ExhibitRepository.getInstance();
        tabFragments = new MutableLiveData<>();
        collectionPage1 = new MutableLiveData<>(repo.getCollectionPage1());
        collectionPage2 = new MutableLiveData<>(repo.getCollectionPage2());
        collectionPage3 = new MutableLiveData<>(repo.getCollectionPage3());

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TabFragment.newInstance(1));
        fragments.add(TabFragment.newInstance(2));
        fragments.add(TabFragment.newInstance(3));
        tabFragments.setValue(fragments);
    }

    public MutableLiveData<List<Collection>> getCollectionPage(int page) {
        if (page == 1) {
            return collectionPage1;
        } else if (page == 2) {
            return collectionPage2;
        } else if (page == 3) {
            return collectionPage3;
        }
        return null;
    }

    public MutableLiveData<List<Fragment>> getTabFragments() {
        return tabFragments;
    }

}

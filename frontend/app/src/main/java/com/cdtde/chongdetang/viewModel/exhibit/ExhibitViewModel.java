package com.cdtde.chongdetang.viewModel.exhibit;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.repository.ExhibitRepository;
import com.cdtde.chongdetang.view.exhibit.TabFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private MutableLiveData<List<Collection>> page1;
    private MutableLiveData<List<Collection>> page2;
    private MutableLiveData<List<Collection>> page3;

    private boolean isInit;

    public ExhibitViewModel() {
        repo = ExhibitRepository.getInstance();
        tabFragments = new MutableLiveData<>();
        page1 = new MutableLiveData<>();
        page2 = new MutableLiveData<>();
        page3 = new MutableLiveData<>();

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(TabFragment.newInstance(i + 1));
        }
        tabFragments.setValue(fragments);
        isInit = false;
    }

    public MutableLiveData<List<Collection>> getPageCollection(int page) {
        if (page == 1) {
            return page1;
        } else if (page == 2) {
            return page2;
        } else if (page == 3) {
            return page3;
        }
        return null;
    }

    public MutableLiveData<List<Fragment>> getTabFragments() {
        return tabFragments;
    }

    public void updateAllCollection() {
        repo.requestAllCollection();
    }

    public void refreshAllCollection() {
        page1.setValue(
                repo.getCollections().stream()
                        .filter(collection -> "sf".equals(collection.getType()))
                        .collect(Collectors.toList())
        );

        page2.setValue(
                repo.getCollections().stream()
                        .filter(collection -> "zk".equals(collection.getType()))
                        .collect(Collectors.toList())
        );

        page3.setValue(
                repo.getCollections().stream()
                        .filter(collection -> "pb".equals(collection.getType()))
                        .collect(Collectors.toList())
        );
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }
}

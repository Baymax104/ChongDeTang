package com.cdtde.chongdetang.viewModel;

import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.repository.IndexRepository;
import com.cdtde.chongdetang.view.exhibit.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class CultureViewModel extends ViewModel {
    private IndexRepository repository;
    private MutableLiveData<List<Fragment>> tabFragments;
    private MutableLiveData<List<Culture>> culturePage1;
    private MutableLiveData<List<Culture>> culturePage2;
    private MutableLiveData<List<Culture>> culturePage3;
    private MutableLiveData<List<Culture>> culturePage4;


    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }


    public CultureViewModel() {
        super();

        repository = IndexRepository.getInstance();
        tabFragments = new MutableLiveData<>();
        culturePage1 = new MutableLiveData<>(repository.getCulturePage1());
        culturePage2 = new MutableLiveData<>(repository.getCulturePage2());
        culturePage3 = new MutableLiveData<>(repository.getCulturePage3());
        culturePage4 = new MutableLiveData<>(repository.getCulturePage4());

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TabFragment.newInstance(1));
        fragments.add(TabFragment.newInstance(2));
        fragments.add(TabFragment.newInstance(3));
        fragments.add(TabFragment.newInstance(4));
        tabFragments.setValue(fragments);

    }

    public void setCulturePage1(List<Culture> culturePage1) {
        repository.setCulturePage1(culturePage1);
        this.culturePage1.setValue(culturePage1);
    }

    public MutableLiveData<List<Culture>> getCulturePage(int page) {
        if (page == 1) {
            return culturePage1;
        } else if (page == 2) {
            return culturePage2;
        } else if (page == 3) {
            return culturePage3;
        } else if (page == 4) {
            return culturePage4;
        }
        return null;
    }

    public String getUrl(int page,int pos){
        if (page == 1) {
            return culturePage1.getValue().get(pos).getUrl();
        } else if (page == 2) {
            return culturePage2.getValue().get(pos).getUrl();
        } else if (page == 3) {
            return culturePage3.getValue().get(pos).getUrl();
        } else if (page == 4) {
            return culturePage4.getValue().get(pos).getUrl();
        }
        return null;
    }

    public MutableLiveData<List<Fragment>> getTabFragments() {
        return tabFragments;
    }

    public void setDetailUel(String detailUel) {
        repository.setCultureDetailUrl(detailUel);
    }

    public String getDetailUel() {
        return repository.getCultureDetailUrl();
    }
}
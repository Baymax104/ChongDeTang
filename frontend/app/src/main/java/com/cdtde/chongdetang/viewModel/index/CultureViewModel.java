package com.cdtde.chongdetang.viewModel.index;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.repository.IndexRepository;
import com.cdtde.chongdetang.view.index.culture.CultureListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CultureViewModel extends ViewModel {
    private IndexRepository repo;
    private MutableLiveData<List<Fragment>> tabFragments;
    private MutableLiveData<List<Culture>> culturePage1;
    private MutableLiveData<List<Culture>> culturePage2;
    private MutableLiveData<List<Culture>> culturePage3;
    private MutableLiveData<List<Culture>> culturePage4;


    public CultureViewModel() {
        repo = IndexRepository.getInstance();
        culturePage1 = new MutableLiveData<>();
        culturePage2 = new MutableLiveData<>();
        culturePage3 = new MutableLiveData<>();
        culturePage4 = new MutableLiveData<>();

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragments.add(CultureListFragment.newInstance(i + 1));
        }
        tabFragments = new MutableLiveData<>(fragments);

        updateAllCulture();
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

    public MutableLiveData<List<Fragment>> getTabFragments() {
        return tabFragments;
    }

    public void updateAllCulture() {
        repo.getAllCulture();
    }

    public void refreshAllCulture() {
        culturePage1.setValue(
                repo.getCultures().stream()
                        .filter(culture -> "beyl".equals(culture.getType()))
                        .collect(Collectors.toList())
        );
        culturePage2.setValue(
                repo.getCultures().stream()
                        .filter(culture -> "shzk".equals(culture.getType()))
                        .collect(Collectors.toList())
        );
        culturePage3.setValue(
                repo.getCultures().stream()
                        .filter(culture -> "zzys".equals(culture.getType()))
                        .collect(Collectors.toList())
        );
        culturePage4.setValue(
                repo.getCultures().stream()
                        .filter(culture -> "tpdk".equals(culture.getType()))
                        .collect(Collectors.toList())
        );
    }

}
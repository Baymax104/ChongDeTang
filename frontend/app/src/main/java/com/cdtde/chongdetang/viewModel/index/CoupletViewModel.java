package com.cdtde.chongdetang.viewModel.index;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.repository.IndexRepository;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 19:06
 * @Version 1
 */
public class CoupletViewModel extends ViewModel {

    private IndexRepository repo;

    private MutableLiveData<List<News>> couplets;

    public CoupletViewModel() {
        repo = IndexRepository.getInstance();
        couplets = new MutableLiveData<>();

        updateAllCouplet();
    }

    public MutableLiveData<List<News>> getCouplets() {
        return couplets;
    }

    public void updateAllCouplet() {
        repo.getNews("mryl");
    }

    public void refreshAllCouplet() {
        couplets.setValue(repo.getNews());
    }
}

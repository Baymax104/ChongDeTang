package com.cdtde.chongdetang.viewModel.index;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.repository.IndexRepository;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 20:11
 * @Version 1
 */
public class CoupletDetailViewModel extends ViewModel {

    private IndexRepository repo;
    private MutableLiveData<News> couplet;

    public CoupletDetailViewModel() {
        repo = IndexRepository.getInstance();
        couplet = new MutableLiveData<>();
    }

    public MutableLiveData<News> getCouplet() {
        return couplet;
    }

    public void setCouplet(News couplet) {
        this.couplet.setValue(couplet);
    }
}

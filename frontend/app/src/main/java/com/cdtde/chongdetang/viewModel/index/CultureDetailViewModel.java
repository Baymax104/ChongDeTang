package com.cdtde.chongdetang.viewModel.index;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.repository.IndexRepository;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 16:50
 * @Version 1
 */
public class CultureDetailViewModel extends ViewModel {

    private IndexRepository repo;

    private MutableLiveData<Culture> culture;

    public CultureDetailViewModel() {
        repo = IndexRepository.getInstance();
        culture = new MutableLiveData<>();
    }

    public MutableLiveData<Culture> getCulture() {
        return culture;
    }

    public void setCulture(Culture culture) {
        this.culture.setValue(culture);
    }
}

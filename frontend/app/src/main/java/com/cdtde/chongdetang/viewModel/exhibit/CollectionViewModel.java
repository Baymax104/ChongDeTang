package com.cdtde.chongdetang.viewModel.exhibit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.repository.ExhibitRepository;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/13 16:23
 * @Version 1
 */
public class CollectionViewModel extends ViewModel {
    private ExhibitRepository repo;

    private MutableLiveData<Collection> collection;

    public CollectionViewModel() {
        repo = ExhibitRepository.getInstance();
        collection = new MutableLiveData<>();
    }

    public MutableLiveData<Collection> getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection.setValue(collection);
    }
}

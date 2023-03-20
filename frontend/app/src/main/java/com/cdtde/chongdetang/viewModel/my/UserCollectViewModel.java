package com.cdtde.chongdetang.viewModel.my;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.repository.MyRepository;
import com.cdtde.chongdetang.view.my.collect.UserCollectionFragment;
import com.cdtde.chongdetang.view.my.collect.UserProductFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/24 20:08
 * @Version 1
 */
public class UserCollectViewModel extends ViewModel {

    private MyRepository repo;

    private List<Fragment> fragments;

    private MutableLiveData<List<Collection>> userCollection;

    private MutableLiveData<List<Product>> userProduct;

    private int currentPage;

    public UserCollectViewModel() {
        repo = MyRepository.getInstance();

        fragments = new ArrayList<>();
        fragments.add(UserCollectionFragment.newInstance());
        fragments.add(UserProductFragment.newInstance());

        userCollection = new MutableLiveData<>();
        userProduct = new MutableLiveData<>();
        currentPage = 1;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public MutableLiveData<List<Collection>> getUserCollection() {
        return userCollection;
    }

    public MutableLiveData<List<Product>> getUserProduct() {
        return userProduct;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void updateUserCollection() {
        repo.requestUserCollection();
    }

    public void updateUserProduct() {
        repo.requestUserProduct();
    }

    public void refreshUserCollection() {
        userCollection.setValue(repo.getCollections());
    }

    public void refreshUserProduct() {
        userProduct.setValue(repo.getProducts());
    }

    public void removeUserCollect(UserCollect userCollect) {
        repo.requestRemoveUserCollect(userCollect);
    }
}

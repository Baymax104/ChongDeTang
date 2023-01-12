package com.cdtde.chongdetang.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.ShopRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/7 15:49
 * @Version 1
 */
public class ShopViewModel extends ViewModel {

    private ShopRepository repository;
    private MutableLiveData<List<Integer>> bannerResource;

    private MutableLiveData<List<Product>> products;

    public ShopViewModel() {
        repository = ShopRepository.getInstance();
        bannerResource = new MutableLiveData<>(repository.getBannerResource());
        products = new MutableLiveData<>(repository.getProducts());
    }

    public MutableLiveData<List<Integer>> getBannerResource() {
        return bannerResource;
    }

    public MutableLiveData<List<Product>> getProducts() {
        return products;
    }

}

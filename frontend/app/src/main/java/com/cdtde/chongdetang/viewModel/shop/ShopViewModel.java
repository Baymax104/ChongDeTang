package com.cdtde.chongdetang.viewModel.shop;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.ShopRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/7 15:49
 * @Version 1
 */
public class ShopViewModel extends ViewModel {

    private ShopRepository repo;
    private MutableLiveData<List<Integer>> bannerResource;

    private MutableLiveData<List<Product>> products;

    private MutableLiveData<List<Product>> hotProducts;

    private boolean isInit;

    public ShopViewModel() {
        repo = ShopRepository.getInstance();
        bannerResource = new MutableLiveData<>(repo.getBannerResource());
        products = new MutableLiveData<>();
        hotProducts = new MutableLiveData<>();
        isInit = false;
    }

    public MutableLiveData<List<Integer>> getBannerResource() {
        return bannerResource;
    }

    public MutableLiveData<List<Product>> getProducts() {
        return products;
    }

    public MutableLiveData<List<Product>> getHotProducts() {
        return hotProducts;
    }

    public Product getHotProduct(int i) {
        List<Product> value = hotProducts.getValue();
        return value != null ? value.get(i) : null;
    }

    public void refreshAllProduct() {
        products.setValue(repo.getProducts());
        hotProducts.setValue(repo.getHotProducts());
    }

    public void updateAllProduct() {
        repo.getAllProduct();
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }
}

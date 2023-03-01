package com.cdtde.chongdetang.viewModel.shop;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.repository.ShopRepository;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/10 18:32
 * @Version 1
 */
public class ProductViewModel extends ViewModel {

    private ShopRepository repo;

    private MutableLiveData<Product> product;

    public ProductViewModel() {
        repo = ShopRepository.getInstance();
        product = new MutableLiveData<>();
    }

    public MutableLiveData<Product> getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product.setValue(product);
    }

    public void addShopping() {
        if (product.getValue() != null) {
            Shopping shopping = new Shopping(1, product.getValue());
            repo.requestAddShopping(shopping);
        }
    }

    public boolean isLogin() {
        return repo.getUser().getToken() != null;
    }

    public void addUserCollect() {
        if (product.getValue() != null) {
            UserCollect userCollect = new UserCollect(product.getValue());
            repo.requestAddUserCollect(userCollect);
        }
    }
}

package com.cdtde.chongdetang.requester.shop;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.ProductRepository;
import com.cdtde.chongdetang.repository.UserCollectRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/7 15:49
 * @Version 1
 */
public class ShopRequester extends Requester {

    private ProductRepository productRepo = ProductRepository.getInstance();

    private UserCollectRepository collectRepo = UserCollectRepository.getInstance();


    public void updateAllProduct(Consumer<List<Product>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Product>> callback = new ReqCallback<>(onSuccess, onFail, this);
        productRepo.requestAllProduct(callback);
    }

    public void updateHotProduct(Consumer<List<Product>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Product>> callback = new ReqCallback<>(onSuccess, onFail, this);
        productRepo.requestHotProduct(callback);
    }

    public void addUserProduct(Product product, Consumer<Product> onSuccess, Consumer<String> onFail) {
        ReqCallback<Product> callback = new ReqCallback<>(onSuccess, onFail, this);
        collectRepo.requestAddUserProduct(product, callback);
    }

    public void removeUserProduct(Product product, Consumer<Product> onSuccess, Consumer<String> onFail) {
        ReqCallback<Product> callback = new ReqCallback<>(onSuccess, onFail, this);
        collectRepo.requestRemoveUserProductWithoutList(product, callback);
    }
}

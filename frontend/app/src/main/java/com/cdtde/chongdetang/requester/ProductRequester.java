package com.cdtde.chongdetang.requester;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.repository.ProductRepository;
import com.cdtde.chongdetang.repository.UserCollectRepository;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/10 18:32
 * @Version 1
 */
public class ProductRequester extends Requester {

    private ProductRepository productRepo = ProductRepository.getInstance();

    private UserCollectRepository collectRepo = UserCollectRepository.getInstance();


    public void addShopping(Product product, Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        productRepo.requestAddShopping(new Shopping(product), callback);
    }

    public void updateUserProduct(Product product, Consumer<Product> onSuccess, Consumer<String> onFail) {
        boolean isCollect = product.isUserCollect();
        ReqCallback<Product> callback = new ReqCallback<>(onSuccess, onFail, this);
        if (isCollect) { // 取消收藏
            collectRepo.requestRemoveUserProductWithoutList(product, callback);
        } else { // 添加收藏
            collectRepo.requestAddUserProduct(product, callback);
        }
    }

}

package com.cdtde.chongdetang.requester;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.UserCollectRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/24 20:08
 * @Version 1
 */
public class UserCollectRequester extends Requester {

    private UserCollectRepository repo = UserCollectRepository.getInstance();

    public void updateUserCollection(Consumer<List<Collection>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Collection>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestUserCollection(callback);
    }

    public void updateUserProduct(Consumer<List<Product>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Product>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestUserProduct(callback);
    }

    public void removeUserCollection(Collection collection,
                                     Consumer<List<Collection>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Collection>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestRemoveUserCollection(collection, callback);
    }

    public void removeUserProduct(Product product,
                                  Consumer<List<Product>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Product>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestRemoveUserProduct(product, callback);
    }
}

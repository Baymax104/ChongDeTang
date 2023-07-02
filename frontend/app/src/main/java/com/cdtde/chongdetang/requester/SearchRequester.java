package com.cdtde.chongdetang.requester;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.CollectionRepository;
import com.cdtde.chongdetang.repository.ProductRepository;
import com.cdtde.chongdetang.repository.SearchRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @Author John
 * @Date 2022/12/25 22:36
 * @Version 1
 */
public class SearchRequester extends Requester {

    private final SearchRepository repo = SearchRepository.getInstance();

    public void searchCollection(String content,
                                 Consumer<List<Collection>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Collection>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestSearchCollection(content, callback);
    }

    public void searchProduct(String content,
                              Consumer<List<Product>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Product>> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestSearchProduct(content, callback);
    }
}

package com.cdtde.chongdetang.requester;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.CheckedShopping;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.functions.Consumer;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/20 18:55
 * @Version 1
 */
public class ShoppingRequester extends Requester {

    private ProductRepository repo = ProductRepository.getInstance();

    public void updateShopping(Consumer<List<CheckedShopping>> onSuccess, Consumer<String> onFail) {
        Consumer<List<Shopping>> transform = shoppings -> {
            List<CheckedShopping> wrap = shoppings.stream()
                    .map(CheckedShopping::new)
                    .collect(Collectors.toList());
            onSuccess.accept(wrap);
        };
        ReqCallback<List<Shopping>> callback = new ReqCallback<>(transform, onFail, this);
        repo.requestShopping(callback);
    }

    public void updateShoppingNumber(CheckedShopping checkedShopping, int updated,
                                     Consumer<Integer> onSuccess, Consumer<String> onFail) {
        Shopping shopping = checkedShopping.getShopping();
        ReqCallback<Integer> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestUpdateShoppingNumber(shopping, updated, callback);
    }

    public void deleteShopping(CheckedShopping checkedShopping,
                               Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        repo.requestDeleteShopping(checkedShopping.getShopping(), callback);
    }
}

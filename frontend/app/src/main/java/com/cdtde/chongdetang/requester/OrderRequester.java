package com.cdtde.chongdetang.requester;

import com.cdtde.chongdetang.base.vm.Requester;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.Order;
import com.cdtde.chongdetang.repository.AddressRepository;
import com.cdtde.chongdetang.repository.OrderRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @ClassName OrderRequester
 * @Author John
 * @Date 2023/7/3 15:51
 * @Version 1.0
 */
public class OrderRequester extends Requester {

    private final AddressRepository addressRepo = AddressRepository.getInstance();

    private final OrderRepository orderRepo = OrderRepository.getInstance();

    public void getAllAddress(Consumer<List<Address>> onSuccess, Consumer<String> onFail) {
        ReqCallback<List<Address>> callback = new ReqCallback<>(onSuccess, onFail, this);
        addressRepo.requestAllAddress(callback);
    }

    public void addOrder(Order order, Consumer<Object> onSuccess, Consumer<String> onFail) {
        ReqCallback<Object> callback = new ReqCallback<>(onSuccess, onFail, this);
        orderRepo.requestAddOrder(order, callback);
    }
}

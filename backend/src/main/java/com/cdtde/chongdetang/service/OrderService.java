package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Orders;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

public interface OrderService {
    /**
     * 获取当前用户所有订单信息
     * @return 订单列表
     */
    Result<List<Orders>> getAllOrders();
    /**
     * 为当前用户添加一个order
     * @param  order
     * @return success
     */
    Result<Object> addOrder(Orders order);
    /**
     * 删除一个订单
     * @param order
     * @return success
     */
    Result<Object> removeOrder(Orders order);

    /**
     * 更改订单支付状态
     * @param order_id
     * @param status
     * @return
     */
    Result<Object> changeStatus(String order_id,String status);
}

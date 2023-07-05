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
     * @param  order order对象
     * @return 结果状态
     */
    Result<Object> addOrder(Orders order);
    /**
     * 删除一个订单
     * @param order order对象
     * @return 结果状态
     */
    Result<Object> removeOrder(Orders order);

    /**
     * 更改订单状态
     * @param orderId order对象id
     * @param status 修改的状态值
     * @return 结果状态
     */
    Result<Object> changeStatus(Integer orderId,String status);
}

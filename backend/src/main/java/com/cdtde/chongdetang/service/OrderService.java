package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Order;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 获取当前用户所有订单信息
     * @return 订单列表
     */
    Result<List<Order>> getAllOrdersByUser();
    /**
     * 为当前用户添加一个order
     * @param  order order对象
     * @return 结果状态
     */
    Result<Object> addOrder(Order order);
    /**
     * 删除一个订单
     * @param order order对象
     * @return 结果状态
     */
    Result<Object> removeOrder(Order order);

    /**
     * 更改订单状态
     * @param orderId order对象id
     * @param status 修改的状态值
     * @return 结果状态
     */
    Result<Object> changeStatus(Integer orderId,String status);

    /**
     * 管理员获取全部订单信息
     * @return 全部订单信息
     */
    Result<List<Order>> getOrdersByAdmin();

    Result<List<Map<String,Double>>> getOrderInfoByDay(Integer days);
}

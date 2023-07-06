package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.OrderMapper;
import com.cdtde.chongdetang.mapper.OrderShoppingMapper;
import com.cdtde.chongdetang.pojo.Order;
import com.cdtde.chongdetang.pojo.OrderShopping;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderShoppingMapper orderShoppingMapper;


    @Override
    public Result<List<Order>> getAllOrdersByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        List<Order> orders = orderMapper.getOrders(userId);
        return new Result<>("success",null,orders);
    }


    @Override
    public Result<Object> addOrder(Order order){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        order.setUserId(userId);
        orderMapper.addOrder(order);
        int orderId = order.getId();
        List<OrderShopping> shoppings = order.getShoppings();
        shoppings.forEach(orderShopping -> orderShopping.setOrderId(orderId));
        orderShoppingMapper.insertOrderShoppingBatch(shoppings);
        return new Result<>("success",null,null);
    }


    @Override
    public Result<Object> removeOrder(Order order){
        orderMapper.removeOrder(order);
        return new Result<>("success",null,null);
    }

    @Override
    public Result<Object> changeStatus(Integer orderId, String status){
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", orderId)
                .set("status", status);
        int i = orderMapper.update(null, wrapper);
        if(i != 1){
            throw new RuntimeException("订单状态更改失败");
        }
        return new Result<>("success",null,null);
    }

    @Override
    public Result<List<Order>> getOrdersByAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            List<Order> orders = orderMapper.getOrdersByAdmin();
            return new Result<>("success", null, orders);
        }
        return new Result<>("无管理员权限", null, null);
    }

    @Override
    public Result<List<Map<String,Double>>> getOrderInfoByDay(Integer days){
        List<Map<String,Double>> result = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < days; i++) {
            Map<String,Double> map = new HashMap<>();
            LocalDate targetDate = currentDate.minusDays(i);

            Integer orderNum = orderMapper.getMatchingOrderCount(targetDate);
            map.put("order_num",Double.valueOf(orderNum));
            Double orderMoney = orderMapper.getOrderTotalAmount(targetDate);
            if(orderMoney == null) orderMoney = 0.0;
            map.put("order_money",orderMoney);
            result.add(map);
        }
        return new Result<>("success",null,result);
    }



}

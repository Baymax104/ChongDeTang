package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.OrdersMapper;
import com.cdtde.chongdetang.mapper.OrderShoppingMapper;
import com.cdtde.chongdetang.pojo.Orders;
import com.cdtde.chongdetang.pojo.OrderShopping;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.pojo.Shopping;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper orderMapper;
    @Autowired
    private OrderShoppingMapper orderShoppingMapper;


    @Override
    public Result<List<Orders>> getAllOrders(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        List<Orders> orders = orderMapper.getOrders(userId);
        return new Result<>("success",null,orders);
    }


    @Override
    public Result<Object> addOrder(Orders order){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        order.setUserId(userId);
        orderMapper.addOrder(order);
        int order_id = order.getId();
        List<Shopping> shoppings = order.getShoppings();
        for (Shopping shopping:shoppings) {
            Integer id = shopping.getId();
            OrderShopping orderShopping = new OrderShopping(order_id,id);
            orderShoppingMapper.insert(orderShopping);
        }
        return new Result<>("success",null,null);
    }


    @Override
    public Result<Object> removeOrder(Orders order){
        int order_id = order.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", order_id);
        orderShoppingMapper.deleteByMap(map);
        orderMapper.removeOrder(order);
        return new Result<>("success",null,null);
    }

    @Override
    public Result<Object> changeStatus(Integer orderId, String status){
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId);
        Orders orders = orderMapper.selectOne(wrapper);
        orders.setStatus(status);
        int i = orderMapper.update(orders,wrapper);
        if(i!=1){
            throw new RuntimeException("订单状态更改失败");
        }
        return new Result<>("success",null,null);
    }

    @Override
    public Result<List<Orders>> getOrdersByAdmin(){
        List<Orders> orders = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            orders = orderMapper.getOrdersByAdmin();
            return new Result<>("success", null, orders);

        }
        return new Result<>("无管理员权限", null, null);
    }



}

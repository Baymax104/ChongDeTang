package com.cdtde.chongdetang.controller;


import com.cdtde.chongdetang.pojo.Orders;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public Result<List<Orders>> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Result<Object> addOrder(@RequestBody Orders order){return orderService.addOrder(order);}

    @DeleteMapping
    public Result<Object> removeOrder(@RequestBody Orders order){return orderService.removeOrder(order);}

    @PostMapping("/change")
    public Result<Object> changeStatus(@RequestBody Map<String,String> map){
        return orderService.changeStatus(map.get("id"),map.get("status"));
    }
}

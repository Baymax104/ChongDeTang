package com.cdtde.chongdetang.controller;


import com.cdtde.chongdetang.pojo.Order;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public Result<List<Order>> getAllOrdersByUser() {
        return orderService.getAllOrdersByUser();
    }

    @GetMapping("/admin")
    public Result<List<Order>> getOrdersByAdmin(){
        return orderService.getOrdersByAdmin();
    }

    @PostMapping
    public Result<Object> addOrder(@RequestBody Order order){
        return orderService.addOrder(order);
    }

    @DeleteMapping
    public Result<Object> removeOrder(@RequestBody Order order){
        return orderService.removeOrder(order);
    }

    @PostMapping("/change")
    public Result<Object> changeStatus(@RequestParam("id") Integer id,
                                       @RequestParam("status") String status){
        return orderService.changeStatus(id, status);
    }
}

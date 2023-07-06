package com.cdtde.chongdetang.controller;


import com.cdtde.chongdetang.pojo.Orders;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrdersService orderService;

    @GetMapping
    public Result<List<Orders>> getAllOrdersByUser() {
        return orderService.getAllOrdersByUser();
    }

    @GetMapping("/admin")
    public Result<List<Orders>> getOrdersByAdmin(){return orderService.getOrdersByAdmin();};

    @PostMapping
    public Result<Object> addOrder(@RequestBody Orders order){return orderService.addOrder(order);}

    @DeleteMapping
    public Result<Object> removeOrder(@RequestBody Orders order){return orderService.removeOrder(order);}

    @PostMapping("/change")
    public Result<Object> changeStatus(@RequestParam("id") Integer id,
                                       @RequestParam("status") String status){
        return orderService.changeStatus(id, status);
    }
}

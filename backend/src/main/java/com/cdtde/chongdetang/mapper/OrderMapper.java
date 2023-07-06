package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> getOrders(@Param("userId") Integer userId);

    List<Order> getOrdersByAdmin();
    void addOrder(@Param("order") Order order);
    void removeOrder(@Param("order") Order order);
    Integer getMatchingOrderCount(@Param("aDate") LocalDate aDate);
    Double getOrderTotalAmount(@Param("today") LocalDate today);








}
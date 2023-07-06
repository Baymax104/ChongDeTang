package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    List<Orders> getOrders(@Param("userId") Integer userId);

    List<Orders> getOrdersByAdmin();
    void addOrder(@Param("order") Orders order);
    void removeOrder(@Param("order") Orders order);
    Integer getMatchingOrderCount(@Param("aDate") LocalDate aDate);
    Double getOrderTotalAmount(@Param("today") LocalDate today);








}
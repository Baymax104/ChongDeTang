package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    List<Orders> getOrders(@Param("userId") Integer userId);
    void addOrder(@Param("order") Orders order);
    void removeOrder(@Param("order") Orders order);






}
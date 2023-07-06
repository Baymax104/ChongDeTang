package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.OrderShopping;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderShoppingMapper extends BaseMapper<OrderShopping> {

    void insertOrderShoppingBatch(List<OrderShopping> orderShoppings);
}

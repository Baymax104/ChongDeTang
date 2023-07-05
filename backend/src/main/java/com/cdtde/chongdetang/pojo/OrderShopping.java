package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderShopping {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private Integer shoppingId;

    public OrderShopping(Integer orderId, Integer shoppingId) {
        this.orderId = orderId;
        this.shoppingId = shoppingId;
    }
}
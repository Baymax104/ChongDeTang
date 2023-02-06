package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 22:53
 * @Version 1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Address {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String province;
    private String city;
    private String detail;
    private String consignee;
    private String phone;
}

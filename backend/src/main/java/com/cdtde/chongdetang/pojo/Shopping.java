package com.cdtde.chongdetang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/20 19:55
 * @Version 1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Shopping {
    private Integer id;
    private Integer userId;
    private Integer number;
    private Product product;
}

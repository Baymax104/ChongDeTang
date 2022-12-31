package com.cdtde.chongdetang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class product {
    private Integer id;
    private Integer brand_id;
    private String product_name;
    private float price;
    private String launch_time;
    private String product_photo;
}
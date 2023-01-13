package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer brandId;
    private String productName;
    private float price;
    @JsonFormat(pattern="yyyy--MM-dd HH:mm:ss")
    private Date launchTime;
    private String productPhoto;
}
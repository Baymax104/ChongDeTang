package com.cdtde.chongdetang.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 22:10
 * @Version 1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Appointment {
    private Integer id;
    private Integer userId;
    private String subscriber;
    private String phone;
    private String number;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderTime;
    private Double orderMoney;
    private String status;
}

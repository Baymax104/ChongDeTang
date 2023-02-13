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
public class Cultural {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String title;
    private String photo;
    private String url;
    private String type;
    private String aabstract;
}
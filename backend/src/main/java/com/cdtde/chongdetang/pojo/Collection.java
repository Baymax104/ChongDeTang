package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String photo;
    private String url;
    private String type;
    private String selected;
    @TableField(exist = false)
    private String userCollect;
}
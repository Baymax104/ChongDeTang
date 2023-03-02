package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String title;
    private String photo;
    private String url;
    private String type;
    private String description;

    public News(String date,String title, String photo,String url,String type,String description) {

        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d = sdf.parse(date);
        } catch (ParseException pe) {
            System.out.println("日期转化失败");
        }
        this.date = d;
        this.title = title;
        this.photo = photo;
        this.url = url;
        this.type = type;
        this.description = description;
    }

}

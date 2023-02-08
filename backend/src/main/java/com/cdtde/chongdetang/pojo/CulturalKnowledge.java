package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CulturalKnowledge {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String date;
    private String title;
    private String photo;
    private String url;
    private String type;
    private String aabstract;

    public CulturalKnowledge(String type, String date,String title,String photo,String url,String aabstract) {
        this.date = date;
        this.url = url;
        this.title = title;
        this.photo = photo;
        this.type = type;
        this.aabstract = aabstract;
    }
}
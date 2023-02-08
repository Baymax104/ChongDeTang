package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionCenter {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String photo;
    private String url;
    private String type;

    public ExhibitionCenter(String type,String title,String photo,String url) {
        this.url = url;
        this.title = title;
        this.photo = photo;
        this.type = type;
    }
}
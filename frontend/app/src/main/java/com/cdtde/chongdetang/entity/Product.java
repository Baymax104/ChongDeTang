package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/8 19:37
 * @Version 1
 */
public class Product extends BaseObservable implements Serializable {

    private Integer id;
    private String name;
    private String photo;
    private Double price;
    private Date launchTime;
    private String introduction;

    public Product() {
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    public Date getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(Date launchTime) {
        this.launchTime = launchTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}

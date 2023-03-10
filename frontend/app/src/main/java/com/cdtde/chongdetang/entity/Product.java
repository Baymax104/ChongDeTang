package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.adapter.DateFormatAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

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
    @JsonAdapter(DateFormatAdapter.class)
    private Date launchTime;
    private String introduction;

    // 坑：boolean类型在转为json时会将is前缀去掉
    private Boolean userCollect;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        notifyPropertyChanged(BR.photo);
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

    @Bindable
    public boolean isUserCollect() {
        return Optional.ofNullable(userCollect).orElse(false);
    }

    public void setUserCollect(boolean userCollect) {
        this.userCollect = userCollect;
        notifyPropertyChanged(BR.userCollect);
    }
}

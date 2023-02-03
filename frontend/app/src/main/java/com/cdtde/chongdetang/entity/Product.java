package com.cdtde.chongdetang.entity;

import android.net.Uri;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;

import java.io.Serializable;

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
    private String image;

    private double price;

    public Product() {
        name = "测试";
        image = UriUtils.res2Uri(String.valueOf(R.drawable.test_picture)).toString();
    }

    public Product(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Product(Integer id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
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
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }
}

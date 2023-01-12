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
    private Uri image;

    private double price;

    public Product() {
        name = "测试";
        image = UriUtils.res2Uri(String.valueOf(R.drawable.test_picture));
    }

    public Product(String name, Uri image) {
        this.name = name;
        this.image = image;
    }

    public Product(Integer id, String name, Uri image) {
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
    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
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

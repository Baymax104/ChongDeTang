package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.Serializable;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/20 20:37
 * @Version 1
 */
public class Shopping extends BaseObservable implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer number;
    private Product product;

    public Shopping() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Bindable
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
        notifyPropertyChanged(BR.number);
    }

    @Bindable
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        notifyPropertyChanged(BR.product);
    }
}

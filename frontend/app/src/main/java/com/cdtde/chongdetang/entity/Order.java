package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.utils.DateFormatSerializer;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Order
 * @Author John
 * @Date 2023/7/3 13:56
 * @Version 1.0
 */
public class Order extends BaseObservable implements Serializable {

    private Integer id;
    private Integer userId;
    @JsonAdapter(DateFormatSerializer.class)
    private Date orderDate;
    private Address address;
    private List<Shopping> shoppings;
    private String status;

    public Order() {
    }

    public Order(Address address, List<Shopping> shoppings) {
        this.address = address;
        this.shoppings = shoppings;
    }

    public Order(Address address, List<Shopping> shoppings, String status) {
        this.address = address;
        this.shoppings = shoppings;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public List<Shopping> getShoppings() {
        return shoppings;
    }

    public void setShoppings(List<Shopping> shoppings) {
        this.shoppings = shoppings;
        notifyPropertyChanged(BR.shoppings);
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    @Bindable
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        notifyPropertyChanged(BR.orderDate);
    }
}

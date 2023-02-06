package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.cdtde.chongdetang.BR;

import java.io.Serializable;
import java.util.Objects;

public class Address extends BaseObservable implements Serializable {
    private Integer id;
    private Integer userId;
    private String province;
    private String city;
    private String detail;
    private String consignee;
    private String phone;

    public Address() {
    }

    public Address(String province, String city, String detail, String consignee, String phone) {
        this.province = province;
        this.city = city;
        this.detail = detail;
        this.consignee = consignee;
        this.phone = phone;
    }

    public Address(Address address) {
        this.id = address.id;
        this.userId = address.userId;
        this.consignee = address.consignee;
        this.phone = address.phone;
        this.province = address.province;
        this.city = address.city;
        this.detail = address.detail;
    }

    @Bindable
    public String getConsignee() {
        return consignee;
    }

    public Address setConsignee(String consignee) {
        this.consignee = consignee;
        notifyPropertyChanged(BR.name);
        return this;
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public Address setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
        return this;
    }

    @Bindable
    public String getDetail() {
        return detail;
    }

    public Address setDetail(String detail) {
        this.detail = detail;
        notifyPropertyChanged(BR.detail);
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Address setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Address setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Bindable
    public String getProvince() {
        return province;
    }

    public Address setProvince(String province) {
        this.province = province;
        notifyPropertyChanged(BR.province);
        return this;
    }

    @Bindable
    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        notifyPropertyChanged(BR.city);
        return this;
    }

}

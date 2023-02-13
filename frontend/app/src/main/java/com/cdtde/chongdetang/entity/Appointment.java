package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.util.adapter.DateFormatAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

public class Appointment extends BaseObservable implements Serializable {

    private Integer id;
    private String subscriber;
    private String phone;
    private String number;
    @JsonAdapter(DateFormatAdapter.class)
    private Date date;
    @JsonAdapter(DateFormatAdapter.class)
    private Date orderTime;
    private Double orderMoney;

    private State state;

    public Appointment() {
    }

    public Appointment(String subscriber, String phone, String number, Date date) {
        this.subscriber = subscriber;
        this.phone = phone;
        this.number = number;
        this.date = date;
    }

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
        notifyPropertyChanged(BR.subscriber);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
        notifyPropertyChanged(BR.number);
    }

    @Bindable
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
        notifyPropertyChanged(BR.orderTime);
    }

    @Bindable
    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
        notifyPropertyChanged(BR.orderMoney);
    }

    @Bindable
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
    }


    public enum State {
        SUCCESS, PROCESSING, FAIL
    }
}

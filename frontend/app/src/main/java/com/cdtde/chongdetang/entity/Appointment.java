package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.cdtde.chongdetang.BR;

import java.io.Serializable;
import java.util.Date;

public class Appointment extends BaseObservable implements Serializable {
    private Date date;
    private int id;
    private Date orderTime;
    private double orderMoney;

    private State state;

    public Appointment() {
        date = new Date();
        id = 0;
        orderTime = new Date();
        orderMoney = 0;
        state = State.FAIL;
    }

    public Appointment(int id, Date date, Date orderTime, double orderMoney, State state) {
        this.date = date;
        this.id = id;
        this.orderTime = orderTime;
        this.orderMoney = orderMoney;
        this.state = state;
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
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
    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
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

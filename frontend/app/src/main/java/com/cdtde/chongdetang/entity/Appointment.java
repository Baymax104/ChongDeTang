package com.cdtde.chongdetang.entity;

import java.util.Date;

public class Appointment {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public enum State {
        SUCCESS, PROCESSING, FAIL
    }
}

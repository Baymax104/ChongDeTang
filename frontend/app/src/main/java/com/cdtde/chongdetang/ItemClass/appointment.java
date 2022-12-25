package com.cdtde.chongdetang.ItemClass;

public class appointment {
    private String date;
    private String orderID;
    private String orderTime;
    private String orderMoney;

    public appointment() {
        date="2022-12-31";
        orderID="CHNM221239944";
        orderTime="2022-12-23 14:58:36";
        orderMoney="0";

    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }



    public String getDate() {
        return date;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderMoney() {
        return orderMoney;
    }




}

package com.cdtde.chongdetang.entity;

public class Address {
    private String name;
    private String phone;
    private String area; // TODO 后续需要接入省市api或者本地存储省市信息
    private String detail;

    public Address(String name, String phone, String area, String detail) {
        this.name = name;
        this.phone = phone;
        this.area = area;
        this.detail = detail;
    }

    public Address() {
        this.name = "王小卤";
        this.phone = "13522380331";
        this.area = "北京市朝阳区南磨房地区";
        this.detail = "平乐园100号北京工业大学平乐园100号北京工业大学平乐园100号北京工业大学";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

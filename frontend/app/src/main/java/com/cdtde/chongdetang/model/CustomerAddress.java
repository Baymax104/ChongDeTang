package com.cdtde.chongdetang.model;

public class CustomerAddress {
    private String name;
    private String phone;
    private String position;
    private String detailAddress;

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public CustomerAddress(String name, String phone, String position, String detailAddress) {
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.detailAddress = detailAddress;
    }
    public CustomerAddress() {
        this.name = "王小卤";
        this.phone = "13522380331";
        this.position = "北京市朝阳区南磨房地区";
        this.detailAddress = "平乐园100号北京工业大学";
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPosition() {
        return position;
    }

    public String getDetailAddress() {
        return detailAddress;
    }
}

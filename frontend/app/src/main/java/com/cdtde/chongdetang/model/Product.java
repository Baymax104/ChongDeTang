package com.cdtde.chongdetang.model;

import com.cdtde.chongdetang.R;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/8 19:37
 * @Version 1
 */
public class Product {
    private String name;
    private int localImg;

    public Product() {
        name = "测试";
        localImg = R.drawable.test_picture;
    }

    public Product(String name, int localImg) {
        this.name = name;
        this.localImg = localImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocalImg() {
        return localImg;
    }

    public void setLocalImg(int localImg) {
        this.localImg = localImg;
    }
}

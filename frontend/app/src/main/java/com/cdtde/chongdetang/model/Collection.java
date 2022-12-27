package com.cdtde.chongdetang.model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.cdtde.chongdetang.R;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 16:03
 * @Version 1
 */
public class Collection {
    private String name;
    private int localImg;
    private String description;

    public Collection() {
        name = "测试";
        localImg = R.drawable.test_picture;
    }

    public Collection(String name, int localImg, String description) {
        this.name = name;
        this.localImg = localImg;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

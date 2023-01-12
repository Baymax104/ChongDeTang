package com.cdtde.chongdetang.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.R;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 16:03
 * @Version 1
 */
public class Collection {
    private int id;
    private String name;
    private Uri image;
    private String description;

    public Collection() {
        name = "测试";
        image = UriUtils.res2Uri(String.valueOf(R.drawable.test_picture));
    }

    public Collection(String name, Uri image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Collection(int id, String name, Uri image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}

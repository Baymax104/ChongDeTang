package com.cdtde.chongdetang.entity;

import android.net.Uri;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;

import java.io.Serializable;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 16:03
 * @Version 1
 */
public class Collection extends BaseObservable implements Serializable {
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

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }
}

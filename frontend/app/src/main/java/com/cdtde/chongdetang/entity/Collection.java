package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.cdtde.chongdetang.BR;

import java.io.Serializable;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 16:03
 * @Version 1
 */
public class Collection extends BaseObservable implements Serializable {

    private Integer id;
    private String title;
    private String photo;
    private String url;
    private String type;
    private String userCollect;

    public Collection() {
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
        notifyPropertyChanged(BR.photo);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public boolean isUserCollect() {
        return "1".equals(userCollect);
    }

    public void setUserCollect(boolean userCollect) {
        this.userCollect = userCollect ? "1" : "0";
        notifyPropertyChanged(BR.userCollect);
    }
}

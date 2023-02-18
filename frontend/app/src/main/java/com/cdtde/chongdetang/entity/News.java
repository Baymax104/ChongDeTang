package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.util.adapter.DateFormatAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/14 17:34
 * @Version 1
 */
public class News extends BaseObservable implements Serializable {
    private Integer id;
    private String title;
    @JsonAdapter(DateFormatAdapter.class)
    private Date date;
    private String description;
    private String photo;
    private String url;
    private String type;

    public News() {
        title = "测试";
        date = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
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
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
        notifyPropertyChanged(BR.photo);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

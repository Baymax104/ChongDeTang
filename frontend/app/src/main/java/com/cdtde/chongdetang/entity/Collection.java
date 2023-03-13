package com.cdtde.chongdetang.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;

import java.io.Serializable;
import java.util.Optional;

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
    private Boolean userCollect;

    public Collection() {
        title = "测试";
        photo = UriUtils.res2Uri(String.valueOf(R.drawable.test_picture)).toString();
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
        return Optional.ofNullable(userCollect).orElse(false);
    }

    public void setUserCollect(boolean userCollect) {
        this.userCollect = userCollect;
        notifyPropertyChanged(BR.userCollect);
    }
}

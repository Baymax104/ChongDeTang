package com.cdtde.chongdetang.entity;

import android.net.Uri;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;

import java.util.Date;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/9 19:32
 * @Version 1
 */

public class User extends BaseObservable {
    private static User user;
    private String name;
    private Uri photo;
    private String gender;
    private Date birthday;
    private String phone;
    private String password;
    private String mail;
    private String token;


    private User() {
        name = "小明";
        gender = "未填写";
        birthday = null;
        phone = "123123123";
        mail = "未填写";
        photo = UriUtils.res2Uri(String.valueOf(R.drawable.user_icon));
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Bindable
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        notifyPropertyChanged(BR.token);
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
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
        notifyPropertyChanged(BR.mail);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
        notifyPropertyChanged(BR.photo);
    }
}

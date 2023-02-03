package com.cdtde.chongdetang.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/9 19:32
 * @Version 1
 */

public class User extends BaseObservable implements Parcelable {
    @SerializedName("username")
    private String name;
    private String photo = UriUtils.res2Uri(String.valueOf(R.drawable.user_icon)).toString();
    private String gender;
    private Date birthday;
    private String phone;
    private String password; // md5值
    private String mail;
    private String token;


    public User() {
    }

    public User(String name, String photo, String gender, Date birthday, String phone, String password, String mail, String token) {
        this.name = name;
        this.photo = photo;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.password = password;
        this.mail = mail;
        this.token = token;
    }

    public static User newInstance() {
        return new User();
    }

    @Bindable
    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        notifyPropertyChanged(BR.token);
        return this;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        return this;
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
        return this;
    }

    @Bindable
    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
        return this;
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
        return this;
    }

    @Bindable
    public String getMail() {
        return mail;
    }

    public User setMail(String mail) {
        this.mail = mail;
        notifyPropertyChanged(BR.mail);
        return this;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
        return this;
    }

    @Bindable
    public String getPhoto() {
        return photo;
    }

    public User setPhoto(String photo) {
        this.photo = photo;
        notifyPropertyChanged(BR.photo);
        return this;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            String name = source.readString();
            String uri = source.readString();
            String gender = source.readString();
            String date = source.readString();
            String phone = source.readString();
            String password = source.readString();
            String mail = source.readString();
            String token = source.readString();
            String photo = uri != null ? uri : UriUtils.res2Uri(String.valueOf(R.drawable.user_icon)).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date birthday;
            try {
                birthday = date != null ? format.parse(date) : null;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return new User(name, photo, gender, birthday, phone, password, mail, token);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeString(gender);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String date = birthday != null ? format.format(birthday) : null;
        dest.writeString(date);
        dest.writeString(phone);
        dest.writeString(password);
        dest.writeString(mail);
        dest.writeString(token);
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", photo=" + photo +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

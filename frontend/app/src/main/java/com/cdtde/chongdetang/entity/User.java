package com.cdtde.chongdetang.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;

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
    private String name;
    private Uri photo = UriUtils.res2Uri(String.valueOf(R.drawable.user_icon));
    private String gender;
    private Date birthday;
    private String phone = "13649423971";
    private String password; // md5值
    private String mail;
    private String token;


    public User() {
    }

    public User(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public User(String name, Uri photo, String gender, Date birthday, String phone, String password, String mail, String token) {
        this.name = name;
        this.photo = photo;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.password = password;
        this.mail = mail;
        this.token = token;
    }

    public static User getInitialInstance() {
        return new User("未登录", "123123");
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
            Uri photo = uri != null ? Uri.parse(uri) : UriUtils.res2Uri(String.valueOf(R.drawable.user_icon));
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
        String uri = photo != null ? photo.toString() : null;
        dest.writeString(uri);
        dest.writeString(gender);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String date = birthday != null ? format.format(birthday) : null;
        dest.writeString(date);
        dest.writeString(phone);
        dest.writeString(password);
        dest.writeString(mail);
        dest.writeString(token);
    }
}

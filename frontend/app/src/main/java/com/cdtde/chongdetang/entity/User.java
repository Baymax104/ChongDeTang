package com.cdtde.chongdetang.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.blankj.utilcode.util.UriUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.google.gson.annotations.Expose;

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

    @Expose
    private Integer id;
    @Expose
    private String username;
    // 在Android端photo字段存储本地file目录下的uri路径
    @Expose
    private String photo;
    @Expose
    private String gender;
    @Expose
    private Date birthday;
    @Expose
    private String phone;
    @Expose
    private String password; // 加密值
    @Expose
    private String mail;
    @Expose
    private String token;

    public static final String DEFAULT_PHOTO = UriUtils.res2Uri(String.valueOf(R.drawable.default_photo)).toString();

    public User() {
    }

    public User(Integer id, String username, String photo, String gender, Date birthday, String phone, String password, String mail, String token) {
        this.id = id;
        this.username = username;
        this.photo = photo;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.password = password;
        this.mail = mail;
        this.token = token;
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.photo = user.photo;
        this.gender = user.gender;
        this.birthday = user.birthday != null ? (Date) user.birthday.clone() : null;
        this.phone = user.phone;
        this.password = user.password;
        this.mail = user.mail;
        this.token = user.token;
    }

    public static User newInstance() {
        return new User();
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
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
            Integer id = source.readInt();
            String name = source.readString();
            String photo = source.readString();
            String gender = source.readString();
            String date = source.readString();
            String phone = source.readString();
            String password = source.readString();
            String mail = source.readString();
            String token = source.readString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date birthday;
            try {
                birthday = date != null ? format.parse(date) : null;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return new User(id, name, photo, gender, birthday, phone, password, mail, token);
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
        dest.writeInt(id);
        dest.writeString(username);
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
                "id=" + id +
                ", username='" + username + '\'' +
                ", photo='" + photo + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String photo;
    private String mail;
    private String phone;
    private String gender;

    @TableField(exist = false)
    private String token;

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public boolean infoEquals(User user) {
        return ObjectUtils.nullSafeEquals(photo, user.photo) &&
                ObjectUtils.nullSafeEquals(username, user.username) &&
                ObjectUtils.nullSafeEquals(gender, user.gender) &&
                ObjectUtils.nullSafeEquals(birthday, user.birthday) &&
                ObjectUtils.nullSafeEquals(mail, user.mail);
    }
}

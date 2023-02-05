package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String birthday;
    private String photo;
    private String mail;
    private String phone;
    private String gender;
    private Integer age;

    @TableField(exist = false)
    private String token;

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}

package com.cdtde.chongdetang.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.user;
import com.cdtde.chongdetang.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> register(String username, String password, String confirmPassword,String mail,String phone) {
        Map<String,String> map = new HashMap<>();
        if(username == null){
            map.put("error_message","用户名不能为空");
            return map;
        }

        username = username.trim();
        if(username.length()==0){
            map.put("error_message","用户名不能为空");
            return map;
        }

        if(username.length()>40){
            map.put("error_message","用户名长度不能大于40");
            return map;
        }

        QueryWrapper<user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<user> accounts = userMapper.selectList(queryWrapper);
        if(!accounts.isEmpty()){
            map.put("error_message","用户名已存在");
            return map;
        }

        if(password == null || confirmPassword == null){
            map.put("error_message","密码不能为空");
            return map;
        }

        if(password.length() == 0 || confirmPassword.length() == 0){
            map.put("error_message","密码不能为空");
            return map;
        }

        if(password.length() > 100 || confirmPassword.length() > 100){
            map.put("error_message","密码长度不能大于100");
            return map;
        }

        if(!password.equals(confirmPassword)){
            map.put("error_message","两次输入的密码不一致");
            return map;
        }

        if(phone == null){
            map.put("error_message","电话号不能为空");
            return map;
        }

        if(mail == null){
            map.put("error_message","邮箱不能为空");
        }

        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/167993_sm_58204cd6ee.jpg";
        user user = new user(null,username,encodedPassword,photo,mail,phone);
        userMapper.insert(user);
        map.put("error_message","success");
        return map;
    }
}

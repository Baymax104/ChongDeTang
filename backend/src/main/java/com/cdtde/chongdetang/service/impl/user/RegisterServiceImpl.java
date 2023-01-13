package com.cdtde.chongdetang.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> register(String phone, String password, String confirmedPassword) {
        Map<String,String> map = new HashMap<>();
        if(phone == null){
            map.put("error_message","手机号不能为空");
            return map;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        if(phone.length()!=11 || !pattern.matcher(phone).matches()){
            map.put("error_message","手机号格式错误");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        List<User> accounts = userMapper.selectList(queryWrapper);
        if(!accounts.isEmpty()){
            map.put("error_message","手机号已被注册");
            return map;
        }
        if(password == null || confirmedPassword == null){
            map.put("error_message","密码不能为空");
            return map;
        }

        if(password.length() == 0 || confirmedPassword.length() == 0){
            map.put("error_message","密码不能为空");
            return map;
        }

        if(password.length() > 100 || confirmedPassword.length() > 100){
            map.put("error_message","密码长度不能大于100");
            return map;
        }

        if(!password.equals(confirmedPassword)){
            map.put("error_message","两次输入的密码不一致");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(null,null,encodedPassword,null,null,phone,null,null);
        userMapper.insert(user);
        map.put("error_message","success");
        return map;
    }
}

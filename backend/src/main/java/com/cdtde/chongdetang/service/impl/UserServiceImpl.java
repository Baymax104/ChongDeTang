package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.LoginUser;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.UserService;
import com.cdtde.chongdetang.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/3 14:55
 * @Version 1
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult<User> login(String phone, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);    //登陆失败会自动处理
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        user.setToken(jwt);

        ResponseResult<User> result = new ResponseResult<>();
        result.setStatus("success").setData(user);
        return result;
    }

    @Override
    public Map<String, Object> register(String phone, String password) {
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        List<User> accounts = userMapper.selectList(queryWrapper);
        if(!accounts.isEmpty()){
            map.put("status", "error");
            map.put("message", "手机号已被注册");
            return map;
        }

        String encrypt = passwordEncoder.encode(password);
        User user = new User(phone, encrypt);
        userMapper.insert(user);

        // 根据用户id生成默认用户名
        String username = "用户" + (user.getId() + 1000);
        user.setUsername(username);
        userMapper.update(user, queryWrapper);

        map.put("status","success");
        return map;
    }
}

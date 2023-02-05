package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.UserService;
import com.cdtde.chongdetang.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/3 14:55
 * @Version 1
 */

@Service
@Slf4j
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
    public ResponseResult<User> updateInfo(User user) {
        ResponseResult<User> result = new ResponseResult<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();

        // 判断token与用户是否匹配
        if (id != user.getId()) {
            log.error("用户信息错误");
            result.setStatus("error").setMessage("用户信息错误");
            return result;
        }

        int row = userMapper.updateById(user);
        if (row != 1) {
            log.error("用户信息修改失败");
            result.setStatus("error").setMessage("用户信息修改失败");
            return result;
        }

        result.setStatus("success").setData(user);
        return result;
    }

    @Override
    public ResponseResult<Object> register(String phone, String password) {
        ResponseResult<Object> result = new ResponseResult<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        List<User> accounts = userMapper.selectList(queryWrapper);
        if(!accounts.isEmpty()){
            log.error("手机号已被注册");
            result.setStatus("error").setMessage("手机号已被注册");
            return result;
        }

        String encrypt = passwordEncoder.encode(password);
        User user = new User(phone, encrypt);
        userMapper.insert(user);

        // 根据用户id生成默认用户名
        String username = "用户" + (user.getId() + 1000);
        user.setUsername(username);
        userMapper.update(user, queryWrapper);

        result.setStatus("success");
        return result;
    }

    @Override
    public ResponseResult<String> updatePassword(String oldPassword, String newPassword) {
        ResponseResult<String> result = new ResponseResult<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            result.setStatus("error").setMessage("输入与原密码不一致！");
            log.error("输入与原密码不一致");
            return result;
        }

        String newEncrypt = passwordEncoder.encode(newPassword);

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId()).set("password", newEncrypt);
        int update = userMapper.update(user, wrapper);
        if (update != 1) {
            result.setStatus("error").setMessage("密码修改失败");
            log.error("密码修改失败");
            return result;
        }

        result.setStatus("success").setData(newEncrypt);
        return result;
    }

    @Override
    public ResponseResult<Object> updatePhone(String phone) {
        ResponseResult<Object> result = new ResponseResult<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId()).set("phone", phone);
        int update = userMapper.update(user, wrapper);
        if (update != 1) {
            result.setStatus("error").setMessage("手机号修改失败");
            log.error("手机号修改失败");
            return result;
        }

        result.setStatus("success");
        return result;
    }
}

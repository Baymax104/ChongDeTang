package com.cdtde.chongdetang.service.impl;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.UserCollectMapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.*;
import com.cdtde.chongdetang.service.CosService;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.UserService;
import com.cdtde.chongdetang.utils.JwtUtil;
import com.qcloud.cos.exception.CosClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Autowired
    private CosService cosService;

    @Autowired
    private UserCollectMapper userCollectMapper;

    @Override
    public ResponseResult<User> login(String phone, String password) {
        ResponseResult<User> result = new ResponseResult<>();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);    //登陆失败会自动处理
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        user.setToken(jwt);

        // 用户头像转码
        String objectKey = user.getPhoto();
        if (objectKey != null) {
            String filename = "user_" + user.getId() + ".jpg";
            File file = new File("src/main/resources/static/imgs", filename);
            try {
                cosService.download(file, objectKey);
            } catch (CosClientException | InterruptedException e) {
                throw new RuntimeException("登录头像获取失败");
            }
            String encode = Base64.encode(file);
            user.setPhoto(encode);
        }

        result.setStatus("success").setData(user);
        return result;
    }

    @Override
    public ResponseResult<User> updateInfo(User user, String newPhoto) {
        ResponseResult<User> result = new ResponseResult<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();

        // 判断token与用户是否匹配
        if (id != user.getId()) {
            throw new RuntimeException("用户信息错误");
        }

        // 头像有修改，进行上传
        String objectKey = null;
        if (newPhoto != null) {
            String filename = "user_" + id + ".jpg";
            File dest = new File("src/main/resources/static/imgs", filename);
            File file = Base64.decodeToFile(newPhoto, dest);
            objectKey = "img/user_photo/" + filename;
            try {
                cosService.upload(file, objectKey);
            } catch (CosClientException | InterruptedException e) {
                throw new RuntimeException("头像上传失败");
            }
        }

        // 字段有变化时才修改，防止修改错误误判
        String uriPhoto = user.getPhoto();
        User dbUser = userMapper.selectById(user.getId());
        user.setPhoto(objectKey);
        if (dbUser != null && dbUser.infoEquals(user)) {
            user.setPhoto(uriPhoto);
            result.setStatus("success").setData(user);
            return result;
        } else if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPhoto(uriPhoto);

        // 用户字段有修改
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId())
                .set("username", user.getUsername())
                .set("gender", user.getGender())
                .set("birthday", user.getBirthday())
                .set("mail", user.getMail())
                .set("photo", objectKey);

        int row = userMapper.update(null, wrapper);
        if (row != 1) {
            throw new RuntimeException("用户信息修改失败");
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
            throw new RuntimeException("手机号已被注册");
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
            throw new RuntimeException("输入与原密码不一致");
        }

        String newEncrypt = passwordEncoder.encode(newPassword);

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId()).set("password", newEncrypt);
        int update = userMapper.update(user, wrapper);
        if (update != 1) {
            throw new RuntimeException("密码修改失败");
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
            throw new RuntimeException("手机号修改失败");
        }

        result.setStatus("success");
        return result;
    }

    @Override
    public ResponseResult<List<UserCollect>> getUserCollect(String type) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        List<UserCollect> userCollects;
        if ("collection".equals(type)) {
            userCollects = userCollectMapper.getUserCollection(userId);
        } else if ("product".equals(type)) {
            userCollects = userCollectMapper.getUserProduct(userId);
        } else {
            throw new RuntimeException("获取路径错误");
        }

        return new ResponseResult<>("success", null, userCollects);
    }

    @Override
    public ResponseResult<Object> addUserCollect(UserCollect userCollect) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        if (userId != userCollect.getUserId()) {
            throw new RuntimeException("用户信息错误");
        }

        int i = userCollectMapper.insertUserCollect(userCollect);
        if (i != 1) {
            throw new RuntimeException("添加用户收藏错误");
        }

        return new ResponseResult<>("success", null, null);
    }
}

package com.cdtde.chongdetang.service.impl;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.FeedbackMapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.*;
import com.cdtde.chongdetang.service.CosService;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.UserService;
import com.cdtde.chongdetang.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cos.url}")
    private String urlFront;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public Result<User> login(String phone, String password) {
        Result<User> result = new Result<>();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);    //登陆失败会自动处理
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        user.setToken(jwt);

        // 用户头像转码
//        String objectKey = user.getPhoto();
//        if (objectKey != null) {
//            String filename = "user_" + user.getId() + ".jpg";
//            File file = new File("src/main/resources/static/imgs", filename);
//            try {
//                cosService.download(file, objectKey);
//            } catch (CosClientException | InterruptedException e) {
//                throw new RuntimeException("登录头像获取失败");
//            }
//            String encode = Base64.encode(file);
//            user.setPhoto(encode);
//        }

        result.setStatus("success").setData(user);
        return result;
    }

    @Override
    public Result<User> updateInfo(User newUser) {
        Result<User> result = new Result<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();

        // 判断token与用户是否匹配
        if (id != newUser.getId()) {
            throw new RuntimeException("用户信息错误");
        }

        // 头像有修改，进行上传
        if (!newUser.getPhoto().endsWith(".jpg")) {
            String filename = "user_" + id + ".jpg";
            File dest = new File("src/main/resources/static/imgs", filename);
            File file = Base64.decodeToFile(newUser.getPhoto(), dest);
            String objectKey = "img/user_photo/" + filename;
            try {
                cosService.upload(file, objectKey);
                newUser.setPhoto(objectKey);
            } catch (InterruptedException e) {
                throw new RuntimeException("头像上传失败");
            }
        }

        User dbUser = userMapper.selectById(newUser.getId());
        if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 只修改部分字段
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", newUser.getId())
                .set("username", newUser.getUsername())
                .set("gender", newUser.getGender())
                .set("birthday", newUser.getBirthday())
                .set("mail", newUser.getMail())
                .set("photo", newUser.getPhoto());

        userMapper.update(null, wrapper);

        result.setStatus("success").setData(newUser);
        return result;
    }

    @Override
    public Result<Object> register(String phone, String password) {
        Result<Object> result = new Result<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        List<User> accounts = userMapper.selectList(queryWrapper);
        if (!accounts.isEmpty()) {
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
    public Result<String> updatePassword(String oldPassword, String newPassword) {
        Result<String> result = new Result<>();
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
    public Result<Object> updatePhone(String phone) {
        Result<Object> result = new Result<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("phone", phone);
        List<User> users = userMapper.selectList(query);
        if (!users.isEmpty()) {
            throw new RuntimeException("手机号已存在");
        }
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
    public Result<Object> setAdmin(String phone, String mode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = userMapper.selectOne(queryWrapper);
        user.setAdmin(mode);
        int i = userMapper.update(user, queryWrapper);
        if (i != 1) {
            throw new RuntimeException("管理员设置错误");
        }
        return new Result<>("success", null, null);
    }

    @Override
    public Result<List<User>> getAllUser() {
        Result<List<User>> res = new Result<>();

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();

        if (isAdmin.equals("0")) {
            res.setStatus("not admin");
            return res;
        }
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.ne("phone",-1);
        List<User> users = userMapper.selectList(null);
        users.forEach(user -> {
            if (user.getPhoto() != null) {
                user.setPhoto(urlFront + user.getPhoto());
            }
        });
        res.setData(users);
        res.setStatus("success");

        return res;
    }

    @Override
    public Result<Object> addFeedback(String content) {
        LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = principal.getUser().getId();

        Feedback feedback = new Feedback();
        feedback.setUserId(userId).setContent(content);
        int insert = feedbackMapper.insert(feedback);
        if (insert != 1) {
            throw new RuntimeException("添加反馈失败");
        }

        return new Result<>("success", null, null);
    }


    @Override
    public Result<Object> checkToken(String token) {
//        if(!JwtUtil.validateToken(token)){
//            return new ResponseResult<Object>("error","invaild token",null);
//        }
        LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();
        String admin = user.getAdmin();
        if (admin.equals("1")) {
            return new Result<>("success", "is admin", null);
        } else {
            return new Result<>("error", "not admin", null);
        }

    }

}

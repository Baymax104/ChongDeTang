package com.cdtde.chongdetang.service.impl;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.FeedbackMapper;
import com.cdtde.chongdetang.mapper.UserCollectMapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.Feedback;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.pojo.UserCollect;
import com.cdtde.chongdetang.service.CosService;
import com.cdtde.chongdetang.service.LoginUser;
import com.cdtde.chongdetang.service.UserService;
import com.cdtde.chongdetang.utils.JwtUtil;
import com.qcloud.cos.exception.CosClientException;
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

    @Autowired
    private UserCollectMapper userCollectMapper;
    @Value("${cos.url}")
    private String urlFront;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public ResponseResult<User> login(String phone, String password) {
        ResponseResult<User> result = new ResponseResult<>();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);    //???????????????????????????
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        user.setToken(jwt);

        // ??????????????????
        String objectKey = user.getPhoto();
        if (objectKey != null) {
            String filename = "user_" + user.getId() + ".jpg";
            File file = new File("src/main/resources/static/imgs", filename);
            try {
                cosService.download(file, objectKey);
            } catch (CosClientException | InterruptedException e) {
                throw new RuntimeException("????????????????????????");
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

        // ??????token?????????????????????
        if (id != user.getId()) {
            throw new RuntimeException("??????????????????");
        }

        // ??????????????????????????????
        String objectKey = null;
        if (newPhoto != null) {
            String filename = "user_" + id + ".jpg";
            File dest = new File("src/main/resources/static/imgs", filename);
            File file = Base64.decodeToFile(newPhoto, dest);
            objectKey = "img/user_photo/" + filename;
            try {
                cosService.upload(file, objectKey);
            } catch (CosClientException | InterruptedException e) {
                throw new RuntimeException("??????????????????");
            }
        }

        // ??????????????????????????????????????????????????????
        String uriPhoto = user.getPhoto();
        User dbUser = userMapper.selectById(user.getId());
        user.setPhoto(objectKey);
        if (dbUser != null && dbUser.infoEquals(user)) {
            user.setPhoto(uriPhoto);
            result.setStatus("success").setData(user);
            return result;
        } else if (dbUser == null) {
            throw new RuntimeException("???????????????");
        }
        user.setPhoto(uriPhoto);

        // ?????????????????????
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId())
                .set("username", user.getUsername())
                .set("gender", user.getGender())
                .set("birthday", user.getBirthday())
                .set("mail", user.getMail())
                .set("photo", objectKey);

        int row = userMapper.update(null, wrapper);
        if (row != 1) {
            throw new RuntimeException("????????????????????????");
        }

        result.setStatus("success").setData(user);
        return result;
    }

    @Override
    public ResponseResult<Object> register(String phone, String password) {
        ResponseResult<Object> result = new ResponseResult<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        List<User> accounts = userMapper.selectList(queryWrapper);
        if (!accounts.isEmpty()) {
            throw new RuntimeException("?????????????????????");
        }

        String encrypt = passwordEncoder.encode(password);
        User user = new User(phone, encrypt);
        userMapper.insert(user);

        // ????????????id?????????????????????
        String username = "??????" + (user.getId() + 1000);
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
            throw new RuntimeException("???????????????????????????");
        }

        String newEncrypt = passwordEncoder.encode(newPassword);

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId()).set("password", newEncrypt);
        int update = userMapper.update(user, wrapper);
        if (update != 1) {
            throw new RuntimeException("??????????????????");
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
            throw new RuntimeException("?????????????????????");
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
            throw new RuntimeException("??????????????????");
        }

        return new ResponseResult<>("success", null, userCollects);
    }

    @Override
    public ResponseResult<Object> addUserCollect(UserCollect userCollect) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        if (userId != userCollect.getUserId()) {
            throw new RuntimeException("??????????????????");
        }

        int i = userCollectMapper.insertUserCollect(userCollect);
        if (i != 1) {
            throw new RuntimeException("????????????????????????");
        }

        return new ResponseResult<>("success", null, null);
    }

    @Override
    public ResponseResult<Object> removeUserCollect(UserCollect userCollect) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = loginUser.getUser().getId();

        if (userId != userCollect.getUserId()) {
            throw new RuntimeException("??????????????????");
        }

        QueryWrapper<UserCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (userCollect.getCollection() != null) {
            wrapper.eq("collection_id", userCollect.getCollection().getId());
        } else if (userCollect.getProduct() != null) {
            wrapper.eq("product_id", userCollect.getProduct().getId());
        } else {
            throw new RuntimeException("??????????????????");
        }

        int delete = userCollectMapper.delete(wrapper);
        if (delete != 1) {
            throw new RuntimeException("??????????????????");
        }

        return new ResponseResult<>("success", null, null);
    }

    @Override
    public ResponseResult<Object> setAdmin(String phone, String mode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = userMapper.selectOne(queryWrapper);
        user.setAdmin(mode);
        int i = userMapper.update(user, queryWrapper);
        if (i != 1) {
            throw new RuntimeException("?????????????????????");
        }
        return new ResponseResult<>("success", null, null);
    }

    @Override
    public ResponseResult<List<User>> getAllUser() {
        ResponseResult<List<User>> res = new ResponseResult<>();

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
    public ResponseResult<Object> addFeedback(String content) {
        LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = principal.getUser().getId();

        Feedback feedback = new Feedback();
        feedback.setUserId(userId).setContent(content);
        int insert = feedbackMapper.insert(feedback);
        if (insert != 1) {
            throw new RuntimeException("??????????????????");
        }

        return new ResponseResult<>("success", null, null);
    }


    @Override
    public ResponseResult<Object> checkToken(String token) {
//        if(!JwtUtil.validateToken(token)){
//            return new ResponseResult<Object>("error","invaild token",null);
//        }
        LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();
        String admin = user.getAdmin();
        if (admin.equals("1")) {
            return new ResponseResult<>("success", "is admin", null);
        } else {
            return new ResponseResult<>("error", "not admin", null);
        }

    }

}

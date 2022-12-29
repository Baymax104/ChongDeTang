package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.bean.User;
import com.cdtde.chongdetang.dao.UserDao;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/29 16:02
 * @Version 1
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable String id) {
        return userDao.getById(Integer.parseInt(id));
    }
}

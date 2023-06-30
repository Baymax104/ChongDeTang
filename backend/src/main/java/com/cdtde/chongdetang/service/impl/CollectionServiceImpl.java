package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdtde.chongdetang.mapper.CollectionMapper;
import com.cdtde.chongdetang.mapper.UserCollectionMapper;
import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.pojo.UserCollection;
import com.cdtde.chongdetang.service.CollectionService;
import com.cdtde.chongdetang.service.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
@Slf4j
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    @Override
    public Result<List<Collection>> download(String type) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<Collection> collections = collectionMapper.selectList(queryWrapper);

        // 未登录
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("success", null, collections);
        }

        // 已登录
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        List<UserCollection> userCollection = userCollectionMapper.getUserCollection(userId);

        for (UserCollection uc : userCollection) {
            for (Collection c : collections) {
                if (ObjectUtils.nullSafeEquals(uc.getCollection().getId(), c.getId())) {
                    c.setUserCollect("1");
                    break;
                }
            }
        }

        return new Result<>("success", null, collections);
    }

    @Override
    public Result<List<Collection>> getHot() {
        Result<List<Collection>> result = new Result<>();
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selected", 1);
        List<Collection> selectedList = collectionMapper.selectList(queryWrapper);
        result.setStatus("success").setData(selectedList);
        return result;
    }

    @Override
    public Result<List<Collection>> getNotHot() {
        Result<List<Collection>> result = new Result<>();
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selected", 0);
        List<Collection> selectedList = collectionMapper.selectList(queryWrapper);
        result.setStatus("success").setData(selectedList);
        return result;
    }
}
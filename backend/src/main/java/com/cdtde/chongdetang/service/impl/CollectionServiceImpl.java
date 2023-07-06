package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdtde.chongdetang.mapper.CollectionMapper;
import com.cdtde.chongdetang.mapper.UserCollectionMapper;
import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.Product;
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
    public Result<List<Collection>> getCollection(String type) {
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

    @Override
    public Result<List<Collection>> getAllCollectionByAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            List<Collection> products = collectionMapper.selectList(null);
            return new Result<>("success",null,products);
        }
        return new Result<>("error", "您没有管理员权限", null);
    }

    @Override
    public Result<Object> addCollectionByAdmin(Collection collection){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("title",collection.getTitle());
            List<Collection> collections = collectionMapper.selectList(queryWrapper);
            if(!collections.isEmpty()){
                throw new RuntimeException("存在同名藏品");
            }
            int i = collectionMapper.insert(collection);
            if(i != 1){
                throw new RuntimeException("添加藏品失败");
            }
            return new Result<>("success",null,null);
        }

        return new Result<>("error", "您没有管理员权限", null);
    }

    @Override
    public Result<Object> removeCollectionByAdmin(Collection collection) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new Result<>("error", "未登录", null);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String isAdmin = loginUser.getUser().getAdmin();
        if(Objects.equals(isAdmin, "1")){   // 判断管理员身份
            int i = collectionMapper.deleteById(collection);
            if(i!=1){
                throw new RuntimeException("删除藏品失败");
            }
        }
        return new Result<>("success", null, null);
    }
}
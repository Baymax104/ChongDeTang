package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdtde.chongdetang.mapper.CollectionMapper;
import com.cdtde.chongdetang.mapper.UserCollectMapper;
import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.pojo.UserCollect;
import com.cdtde.chongdetang.service.CollectionService;
import com.cdtde.chongdetang.service.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private UserCollectMapper userCollectMapper;

    @Override
    public ResponseResult<List<Collection>> download() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Collection> collections = collectionMapper.selectList(null);

        // 未登录
        if (!(authentication.getPrincipal() instanceof LoginUser)) {
            return new ResponseResult<>("success", null, collections);
        }

        // 已登录
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        List<UserCollect> userCollection = userCollectMapper.getUserCollection(userId);

        Map<Integer, Collection> collectMap = userCollection.parallelStream()
                .map(UserCollect::getCollection)
                .collect(Collectors.toMap(
                        Collection::getId,
                        collection -> collection
                ));

        collections.forEach(collection -> {
            if (collectMap.get(collection.getId()) != null) {
                collection.setUserCollect("1");
            } else {
                collection.setUserCollect("0");
            }
        });

        return new ResponseResult<>("success", null, collections);
    }

    @Override
    public ResponseResult<List<Collection>> getHot() {
        ResponseResult<List<Collection>> result = new ResponseResult<>();
        List<Collection> all = collectionMapper.selectList(null);
        List<Collection> selected;
        // 大于等于4条时随机取4条，否则返回所有记录
        if (all.size() >= 4) {
            Set<Collection> set = new HashSet<>();
            while (set.size() < 4) {
                int index = new Random().nextInt(all.size());
                set.add(all.get(index));
            }
            selected = new ArrayList<>(set);
        } else {
            selected = all;
        }
        result.setStatus("success").setData(selected);
        return result;
    }
}
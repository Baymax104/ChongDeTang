package com.cdtde.chongdetang.service.impl;

import com.cdtde.chongdetang.mapper.CollectionMapper;
import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public ResponseResult<List<Collection>> download(){
        List<Collection> collectionList = collectionMapper.selectList(null);
        ResponseResult<List<Collection>> result = new ResponseResult<>();
        result.setStatus("success").setData(collectionList);
        return result;
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
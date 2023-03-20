package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
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
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selected", 1);
        List<Collection> selectedList = collectionMapper.selectList(queryWrapper);
        result.setStatus("success").setData(selectedList);
        return result;
    }

    @Override
    public ResponseResult<List<Collection>> getNotHot() {
        ResponseResult<List<Collection>> result = new ResponseResult<>();
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selected", 0);
        List<Collection> selectedList = collectionMapper.selectList(queryWrapper);
        result.setStatus("success").setData(selectedList);
        return result;
    }
}
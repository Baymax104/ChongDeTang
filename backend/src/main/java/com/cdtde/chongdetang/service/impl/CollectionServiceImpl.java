package com.cdtde.chongdetang.service.impl;

import com.cdtde.chongdetang.mapper.CollectionMapper;
import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
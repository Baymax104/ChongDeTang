package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface CollectionService {
    ResponseResult<List<Collection>> download();

    ResponseResult<List<Collection>> getHot();
}
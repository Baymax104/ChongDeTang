package com.cdtde.chongdetang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface CollectionService extends IService<Collection> {
    ResponseResult<List<Collection>> download();

    ResponseResult<List<Collection>> getHot();

    ResponseResult<List<Collection>> getNotHot();
}
package com.cdtde.chongdetang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdtde.chongdetang.pojo.Collection;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

public interface CollectionService extends IService<Collection> {
    Result<List<Collection>> download(String type);

    Result<List<Collection>> getHot();

    Result<List<Collection>> getNotHot();
}
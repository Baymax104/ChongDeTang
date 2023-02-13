package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Cultural;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface CulturalService {
    ResponseResult<List<Cultural>> download(String type);
}
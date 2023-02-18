package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Culture;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface CultureService {
    ResponseResult<List<Culture>> download();
}
package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.ExhibitionCenter;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface ExhibitionCenterService {
    ResponseResult<Object> upload(String type, String title, String photo, String url);
    ResponseResult<List<ExhibitionCenter>> download(String type);
}
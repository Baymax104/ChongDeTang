package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.NewsCenter;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface NewsCenterService {
    ResponseResult<Object> upload(String type, String date,String title,String photo,String url,String aabstract);
    ResponseResult<List<NewsCenter>> download(String type);
}

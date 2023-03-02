package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface NewsService {
    ResponseResult<List<News>> download(String type);

    public ResponseResult<Object> upload(String date,String title, String photo,String url,String type,String description);
}

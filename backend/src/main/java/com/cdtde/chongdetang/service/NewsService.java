package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.News;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

public interface NewsService {
    ResponseResult<List<News>> download(String type);
}
